(defn all-vec? [args] (every? vector? args))
(defn vec-of-num? [v] (and (vector? v) (every? number? v)))
(defn vectors? [args] (and (all-vec? args) (every? vec-of-num? args)))
(defn vec-equal-len? [args] (and (vectors? args) (apply == (mapv count args))))
(defn is-matrix? [m] (and (vector? m) (every? vec-of-num? m) (vec-equal-len? m)))
(defn all-matrix? [args] (every? is-matrix? args))
(defn matrices-equal-size? [args]
  (and (apply == (mapv count args))
       (apply == (mapv (fn [m] (count (first m))) args))))
(defn complementary? [& args]
  (if (empty? (rest args))
    true
    (and (== (count (first (first args))) (count (first (rest args))))
         (complementary? (rest args)))))

(defn vecFun [f]
  (fn [& vectors]
    {:pre [(vec-equal-len? vectors)]}
    (apply mapv f vectors)))

(def v+ (vecFun +))
(def v- (vecFun -))
(def v* (vecFun *))
(def vd (vecFun /))

(defn scalar [& vectors]
  {:pre [(vec-equal-len? vectors)]}
  (apply + (apply v* vectors)))

(defn binary-vect-prod [a, b]
  (letfn [(calc [i j]
            (- (* (nth a i) (nth b j)) (* (nth b i) (nth a j))))]
    (vector (calc 1 2) (calc 2 0) (calc 0 1))))
(defn vect [& vectors]
  {:pre [(vec-equal-len? vectors)]}
  (reduce binary-vect-prod vectors))

(defn v*s [vector & scalars]
  {:pre [(and (every? number? scalars) (vec-of-num? vector))]}
  (mapv (fn [coordinate] (* coordinate (apply * scalars))) vector))

(defn matrixFunc [f]
  (fn [& matrices]
    {:pre [(and (all-matrix? matrices) (matrices-equal-size? matrices))]}
    (apply mapv f matrices)))

(def m+ (matrixFunc v+))
(def m- (matrixFunc v-))
(def m* (matrixFunc v*))
(def md (matrixFunc vd))

(defn m*s [matrix & scalars]
  {:pre [(and (vec-of-num? scalars) (is-matrix? matrix))]}
  (mapv (fn [row] (v*s row (apply * scalars))) matrix))
(defn m*v [matrix vector]
  {:pre [(and (is-matrix? matrix) (vec-of-num? vector)
              (== (count (first matrix)) (count vector)))]}
  (mapv (fn [row] (scalar row vector)) matrix))
(defn transpose [matrix]
  (apply mapv vector matrix))
(defn m*m [& matrices]
  {:pre [(and (all-matrix? matrices) (complementary? matrices))]}
  (reduce (fn [a b]
            (mapv (fn [a-row] (m*v (transpose b) a-row)) a)) matrices))

(defn shape [tensor]
  (if (number? tensor)
    ()
    (into [] (cons (count tensor) (shape (first tensor))))))

(defn broadcast [tensor form]
  (mapv
    (fn [item]
      (cond
        (empty? (rest form)) (vector (repeat (first form) item))
        (number? item) (broadcast tensor (rest form))
        :else (broadcast item (rest form))))
    tensor))

;(println (empty? (rest [1])))
;(println (broadcast [[1 2]] [1 2 3]))
;(println (broadcast [1] [1 2 3]))
;(println (shape [[1 2] [2 3]]))
;(println (first (list 1 2)))
;(println (rest (list 1 2)))
;(println (shape [[1 2] [3 4]]))
;(println (type (shape [[1 2] [3 4]])))
(println (broadcast [[1]] [1 2]))
;(println (drop-last (count (shape [[[2 3 4] [5 6 7]]])) [1 2 4]))