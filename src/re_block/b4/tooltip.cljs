(ns re-block.b4.tooltip
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn -tooltip
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [placement position-top position-left
                arrow-offset-top
                arrow-offset-left
                class style ]
         :or {placement "right"}} props
        props (dissoc props :placement :position-top :position-left
                      :arrow-offset-top
                      :arrow-offset-left
                      :class :style)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class
              (u/get-class-set bsp)
              {placement true})
        outer-style (merge {:top position-top
                            :left position-left} style)
        arrow-style {:top arrow-offset-top
                     :left arrow-offset-left}]
    [:div (merge eps {:role "tooltip"
                      :class clss
                      :style outer-style})
     [:div {:class (u/prefix bsp "arrow")
            :style arrow-style}]
     (into [:div {:class (u/prefix bsp "inner")}]
           children)]
    ))

(def tooltip
  (u/bs-class :tooltip -tooltip))

