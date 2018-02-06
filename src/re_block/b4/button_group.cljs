(ns re-block.b4.button-group
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn -button-group
  [props & children ]
  (let [[props children] (if (map? props)
                           [props  children]
                           [{} (into [props]  children)])
        {:keys [block justified vertical class]
         :or {block false justified false vertical false}} props
        props (dissoc props :block :justified :vertical :class)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class
              (u/get-class-set bsp)
              {(u/prefix bsp) (not vertical)
               (u/prefix bsp "vertical") vertical
               (u/prefix bsp "justified" ) justified
               (u/prefix {:bs-class "btn"} "block") block})]
    (print "type " (type children) " and " )
    (into [:div (merge eps {:class clss})] children)))

(def button-group
  (with-meta (u/bs-class "btn-group" -button-group)
    {:display-name "ButtonGroup"}))

