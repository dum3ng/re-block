(ns re-block.b4.utils
  (:require [clojure.string :as string]
            [re-block.utils :as uu]))

(defn update-props-class
  [props cls]
  (update props :class
          #(if (nil? %)
             cls
             (str cls " " %))))



;; react bootstrap
(defn prefix
  ([bs-props]
   (prefix bs-props ""))
  ([bs-props cls]
   (let [bs-cls (-> (or (:bs-class bs-props) "")
                    name string/trim)
         cls (name (or cls ""))]
     (str bs-cls (if (not (string/blank? cls))
                   (str "-" cls)))   )))

(defn get-bs-props
  [props]
  (select-keys props [:bs-class :bs-size :bs-style :bs-role]))

(defn bs-prop?
  [p]
  (#{:bs-class :bs-size :bs-style :bs-role} p))

(def size-map {:large "lg" :medium "md" :small "sm" :xsmall "xs"
               :lg "lg" :md "md" :sm "sm" :xs "xs"})

(defn get-class-set
  [props]
  (-> {(prefix props) true}
      (uu/assoc-if (:bs-size props) (prefix props
                                         (or (size-map (:bs-size props)) (:bs-size props))) true )
      (uu/assoc-if (:bs-style props) (prefix props (:bs-style props)) true)))

(defn split-bs-props
  [props]
  (let [eps (into {} (remove (fn [[k v]]
                               (bs-prop? k)) props))
        bsp (get-bs-props props)]
    [bsp eps]))

(defn bs-class
  [cls view]
  (fn [props & children]
    (let [[props children] (if (map? props)
                             [props children]
                             [{} (into [props ] children)])]
      (apply view (merge {:bs-class cls}
                         props) children ))))

(defn bs-style
  [style view]
  (fn [props & children]
    (let [[props children] (if (map? props)
                             [props children]
                             [{} (into [props ] children)])]
      (apply view (merge {:bs-style style} props)
             children) )))

(defn bs-size
  [size view]
  (fn [props & children]
    (let [[props children] (if (map? props)
                             [props children]
                             [{} (into [props ] children)])]
      (apply view (merge {:bs-size size} props)
             children) )))
