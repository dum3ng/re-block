(ns re-block.b4.badge
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))


(defn -badge
  [props & children]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props] children)])
        {:keys [pull-right class ] :or {pull-right false} } props
        props (dissoc props :pull-right :class)
        [bsp esp] (u/split-bs-props props)
        clss (uu/class
              class
              (merge (u/get-class-set bsp)
                     {:pull-right pull-right}))]
    (into [:span (merge esp {:class clss})] children)))


(def badge
  (u/bs-class :badge -badge))

