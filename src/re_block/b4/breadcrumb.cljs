(ns re-block.b4.breadcrumb
  (:require [re-block.b4.breadcrumb-item :refer [breadcrumb-item]]
            [re-block.b4.utils :as u]
            [re-block.utils :as uu]))

(defn -breadcrumb
  [props & children]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        class {:class  props}
        props (dissoc props :class)
        [bs-props eps] (u/split-bs-props props)
        clss (uu/class
              class
              (u/get-class-set bs-props)) ]
    ;; (print (clj->js children))
    (into [:ol (merge eps
                      {:class clss
                       :role "navigation"
                       :aria-label "breadcrumbs"})]
          children)))


(def breadcrumb
  (with-meta (u/bs-class "breadcrumb"   -breadcrumb)
    {:display-name "Breadcrumb"}) )





