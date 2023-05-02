;=================================hw10=================================
(def constant constantly)
(defn variable [name] (fn [variables] (variables name)))
(defn negate [value] (fn [variables] (- (value variables))))

(defn operation [f]
  (fn [& operands] (fn [variables] (apply f (mapv #(% variables) operands)))))
(def add (operation +))
(def subtract (operation -))
(def multiply (operation *))
(def divide (operation (fn ([] 1)
                         ([arg] (/ 1.0 arg))
                         ([arg & arr] (/ (double arg) (apply * arr))))))
(defn meansq [& args] (fn [variables]
                        (/ ((apply add (mapv #(multiply % %) args)) variables)
                           (count args))))
(defn rms [& args] (fn [variables] (Math/sqrt ((apply meansq args) variables))))

(def ops {'+ add '- subtract '* multiply '/ divide 'negate negate 'meansq meansq 'rms rms})
(defn makeParser [const-impl var-impl operations]
  (fn [expr]
    (letfn [(parse [elements]
              (cond
                (number? elements) (const-impl elements)
                (symbol? elements) (var-impl (str elements))
                (list? elements) (apply (operations (first elements)) (mapv #(parse %) (rest elements)))
                ))]
      (parse (read-string expr)))))
(def parseFunction (makeParser constant variable ops))

;=================================hw11=================================

(load-file "proto.clj")
(def toString (method :toString))
(def evaluate (method :evaluate))
(def diff (method :diff))
(def value (field :value))

(defn createElement [toString evaluate diff]
  (constructor
    (fn [this & value] (assoc this :value value))
    {:toString toString :evaluate evaluate :diff diff}))

(declare ZERO)
(def Constant (createElement
                (fn [this] (str (first (value this))))
                (fn [this vars] (first (value this)))
                (fn [this varDiff] ZERO)))
(def ZERO (Constant 0))
(def ONE (Constant 1))

(def Variable (createElement
                (fn [this] (first (value this)))
                (fn [this vars] (vars (first (value this))))
                (fn [this varDiff] (cond
                                     (= (first (value this)) varDiff) ONE
                                     :else ZERO))))

(defn createOperation [sym operation diffFunc]
  (createElement
    (fn [this] (str "(" sym " " (clojure.string/join " " (map toString (value this))) ")"))
    (fn [this vars] (apply operation (map #(evaluate % vars) (value this))))
    (fn [this varDiff] (diffFunc (into [] (value this)) (map #(diff % varDiff) (value this))))))

(def Add (createOperation
           "+"
           +
           #(apply Add %2)))
(def Subtract (createOperation
                "-"
                -
                #(apply Subtract %2)))
(def Negate (createOperation
              "negate"
              -
              #(apply Negate %2)))

(declare Multiply Sumexp)
(defn diffForMul [args args']
  (apply Add
         (map
           (fn [id] (apply Multiply (assoc args id (nth args' id))))
           (range (count args)))))
(defn diffForSumexp [args args']
  (apply Add (mapv #(Multiply (Sumexp %1) %2) args args')))
(def Multiply (createOperation
                "*"
                *
                diffForMul))
(def Divide (createOperation
              "/"
              (fn
                ([] 1)
                ([one] (/ 1.0 one))
                ([arg & arr] (/ (double arg) (apply * arr))))
              (fn [args args']
                (let [f (first args)
                      s (rest args)
                      f' (first args')
                      s' (rest args')
                      a (apply Multiply s)]
                  (cond
                    (empty? args) ZERO
                    (empty? s) (Negate (Divide f' (Multiply f f)))
                    :else (Divide (Subtract (Multiply f' a) (Multiply f (diffForMul (into [] s) (into [] s')))) (Multiply a a))))
                )))                                         ; (f1/f2f3...fn)' = (f1/a)' = (f1'* a - f * a') / (a * a)
(def Sumexp (createOperation
              "sumexp"
              (fn [& args] (apply + (mapv #(Math/exp %) args)))
              diffForSumexp))
(def LSE (createOperation
           "lse"
           (fn [& args] (Math/log (apply + (mapv #(Math/exp %) args))))
           (fn [args args'] (Divide (diffForSumexp args args') (apply Sumexp args)))))
(def obj-ops {
              '+      Add
              '-      Subtract
              '*      Multiply
              '/      Divide
              'negate Negate
              'sumexp Sumexp
              'lse    LSE
              })
(def parseObject (makeParser Constant Variable obj-ops))