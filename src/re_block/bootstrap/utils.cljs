(ns re-block.bootstrap.utils
  (:require [clojure.string :as string]))


(defn destruct
  [& args]
  (let [[a b & _] args]
    (cond
      (map? a) [a b]
      :else [{} (into [a] b)])))


(defmulti prefix (fn [base fixers] (cond
                                     (map? fixers) :map
                                     :else :string)))
(defmethod prefix :map
  ;; "`fixers` is a map of
  ;;   {:x bool/string}  
  ;;   "
  [base  fixers]
  (mapv (fn [[k v]]
          (cond
            (boolean? v) (if v(str base "-" (name k)))
            (or (string? v) (keyword? v)) (str base "-" (name v))
            :else nil))
        fixers))

(defmethod prefix :string
  [base fixers]
  (cond
    (nil? fixers) nil
    (string? fixers) (if (string/blank? fixers)
                       nil (str base "-" fixers))
    :else (str base "-" fixers)))

(defn with-default-props
  [f d-props]
  (case d-props
    nil f
    {} f
    (fn [props & children]
      (let [[props children] (destruct props children)]
        (apply f (merge d-props props)
               children)))))

(defn with-display-name
  [f s]
  (cond
    (nil? s) f
    :else (with-meta f
            {:display-name s})))

(defn wrap-no-args
  [f]
  (fn
    ([]
     (f {}))
    ([props & children]
     (let [[props children] (destruct props children)]
       (apply f props children )))))

(defn make-view
  [f & {:as m}]
  (let [{:keys [display-name default-props ]} m
        _meta (:meta m)]
    (-> f
        (wrap-no-args )
        (with-default-props default-props)
        (with-display-name display-name)
        (vary-meta merge _meta)))) ;the `with-meta` should be the last wrapper

