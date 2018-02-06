(ns re-block.b4.breadcrumb-item
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]
            [re-block.b4.safe-anchor :refer [safe-anchor]]))

(defn -breadcrumb-item
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [active href title target class] :or {active false}} props
        link-props (select-keys props [:href :title :target])
        props (dissoc  props :active :href :title :target :class)
        clss (uu/class
             class {:active active} )]
    [:li {:class clss}
     (if active
       (into [:span props] children)
       (into [safe-anchor (merge props link-props)] children))
     ]))

(def breadcrumb-item
  (with-meta 
    -breadcrumb-item
    {:display-name "Breadcrumb-item"}))
