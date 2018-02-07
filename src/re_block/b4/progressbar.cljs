(ns re-block.b4.progressbar
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn -progressbar
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

(def progressbar
  (u/bs-class "progressbar" -progressbar))

