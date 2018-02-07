(ns re-block.b4.navbar)

(ns re-block.b4.navbar
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))


(declare navbar-brand navbar-header
         navbar-toggle navbar-collapse)

(defn -navbar
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [] } props
        props (dissoc props )
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              )]
    ))

(def navbar
  (u/bs-style :default  -navbar))


