(defn all-vec? [args] (every? vector? args))
(defn vec-of-num? [v] (and (vector? v) (every? number? v)))
(defn vectors? [args] (all-vec? args))
(defn vec-equal-len? [args] (and (vectors? args) (apply == (mapv count args))))
(defn equal-num-vectors? [args] (and (vec-equal-len? args) (every? vec-of-num? args)))
(defn is-matrix? [m] (and (vector? m) (vec-equal-len? m)))
(defn num-matrix? [m] (and (is-matrix? m) (every? vec-of-num? m)))
(defn all-num-matrix? [args] (every? num-matrix? args))
(defn matrices-equal-size? [args]
  (and (apply == (mapv count args))
       (apply == (mapv (fn [m] (count (first m))) args))))
(defn complementary? [& args]
  (if (empty? (rest args))
    true
    (and (== (count (first (first args))) (count (first (rest args))))
         (complementary? (rest args)))))
(defn tensor? [arg]
  (cond
    (and (vector? arg) (empty? arg)) false
    (or (number? arg) (vec-of-num? arg)) true
    :else (and (vec-equal-len? arg) (every? identity (mapv tensor? arg)))))
(defn all-tensors? [args] (every? tensor? args))

(defn vecFun [f]
  (fn [& vectors]
    {:pre [(equal-num-vectors? vectors)]}
    (apply mapv f vectors)))

(def v+ (vecFun +))
(def v- (vecFun -))
(def v* (vecFun *))
(def vd (vecFun /))

(defn scalar [& vectors]
  {:pre [(equal-num-vectors? vectors)]}
  (apply + (apply v* vectors)))

(defn binary-vect-prod [a, b]
  (letfn [(calc [i j]
            (- (* (nth a i) (nth b j)) (* (nth b i) (nth a j))))]
    (vector (calc 1 2) (calc 2 0) (calc 0 1))))
(defn vect [& vectors]
  {:pre [(equal-num-vectors? vectors)]}
  (reduce binary-vect-prod vectors))

(defn v*s [vector & scalars]
  {:pre [(and (every? number? scalars) (vec-of-num? vector))]}
  (mapv (fn [coordinate] (* coordinate (apply * scalars))) vector))

(defn matrixFunc [f]
  (fn [& matrices]
    {:pre [(and (all-num-matrix? matrices) (matrices-equal-size? matrices))]}
    (apply mapv f matrices)))

(def m+ (matrixFunc v+))
(def m- (matrixFunc v-))
(def m* (matrixFunc v*))
(def md (matrixFunc vd))

(defn m*s [matrix & scalars]
  {:pre [(and (every? number? scalars) (num-matrix? matrix))]}
  (mapv (fn [row] (v*s row (apply * scalars))) matrix))
(defn m*v [matrix vector]
  {:pre [(and (num-matrix? matrix) (vec-of-num? vector)
              (== (count (first matrix)) (count vector)))]}
  (mapv (fn [row] (scalar row vector)) matrix))
(defn transpose [matrix]
  (apply mapv vector matrix))
(defn m*m [& matrices]
  {:pre [(and (all-num-matrix? matrices) (complementary? matrices))]}
  (reduce (fn [a b]
            (mapv (fn [a-row] (m*v (transpose b) a-row)) a)) matrices))

(defn shape [tensor]
  (if (number? tensor)
    ()
    (into [] (cons (count tensor) (shape (first tensor))))))

(defn can-broadcast? [f1 f2]
  (cond
    (or (== 0 (count f1)) (== 0 (count f2))) true
    (== (first f1) (first f2)) (can-broadcast? (rest f1) (rest f2))
    :else false))

(defn broadcast [tensor form]
  {:pre [(can-broadcast? (shape tensor) form)]}
  (if (number? tensor)
    (if (empty? form)
      tensor
      (into [] (repeat (first form) (broadcast tensor (rest form)))))
    (mapv #(broadcast % (rest form)) tensor)))

(defn apply-for-tensors [f vec-of-tensors]
  (if (every? number? vec-of-tensors)
    (apply f vec-of-tensors)
    (mapv #(apply-for-tensors f %) (apply mapv vector vec-of-tensors))))
(defn tensor-broadcast-func [f]
  (fn [& tensors]
    {:pre [(all-tensors? tensors)]}
    (let [form
          (reduce #(if (> (count %1) (count %2)) %1 %2)
                  (mapv #(shape %) tensors))]
      (apply-for-tensors f (mapv #(broadcast % form) tensors)))))

(def tb+ (tensor-broadcast-func +))
(def tb- (tensor-broadcast-func -))
(def tb* (tensor-broadcast-func *))
(def tbd (tensor-broadcast-func /))