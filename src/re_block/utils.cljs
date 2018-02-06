(ns re-block.utils
  (:require [clojure.string :as string]))


(declare dash->camel)

;; general use
(defn assoc-if
  [m pred k v]
  (if pred
    (assoc m k v)
    m))



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
