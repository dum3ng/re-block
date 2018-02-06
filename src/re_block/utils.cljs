(ns re-block.utils
  (:require [clojure.string :as string]))


(declare dash->camel)

;; general use
(defn assoc-if
  ([m pred k v]
   (if pred
     (assoc m k v)
     m))
  ([m pred k v & kvs]
   (if pred
     (apply assoc m k v kvs)
     m)))


;; MARK react use
(defn class
  [& clss]
  (reduce (fn [pre cls]
            (str pre
                 (cond
                   (nil? cls) ""
                   (string? cls) (str cls " ")
                   (map? cls) (reduce-kv
                               (fn [p k v]
                                 (str p " " (if v (name k)))) "" cls)
                   :else (string/join " " (distinct cls)))))
          "" clss))

(defn reagent-props
  [ele]
  {:pre [(vector? ele)]}
  (let [[_ ps & _] ele]
    (if (map? ps)
      ps {})))

(defn clone-element
  " the view is a vector with
  [:tag props  & children]"
  ([view props]
   (clone-element view props nil))
  (  [view props children]
   (let [[tag ps & old] view
         [ps old] (if (map? ps)
                    [ps old]
                    [{} (into [ps] old)])
         props (merge ps props)]
     (if children
       (if (not (vector? (first children))) ; single child
         [tag props children]
         (into [tag props] children))
       (into [tag props ] old)))))
;; js world

(defn $
  [el p]
  (aget el (name p)))

(defn $!
  [el p v]
  (aset el (dash->camel p) v))

(defn $$
  [el & ps]
  (reduce #(assoc %1 %2 ($ el %2)) {} ps))

(defn autobind
  [c & fs]
  (let [proto (aget c "prototype")
        pairs (aget proto "__reactAutoBindPairs")]
    (doseq [f fs]
      (.push pairs (name f) (aget proto (name f))))
    c))

(defn- _dash->camel
  [s]
  (let [dash (name s)
        ds (string/split dash #"-")]
    (reduce #(str %1 (string/capitalize %2))
            ds)   ))

(def dash->camel (memoize _dash->camel))


(defn on
  [el e f]
  (.addEventListener el (name e) f))

(defn off
  [el e f]
  (.removeEventListener el (name e) f))
