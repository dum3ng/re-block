(ns re-block.scrollbars.default-render-elements)

(defn render-view-default
  [p  & children]
  (into [:div p] children))

(defn render-track-horizontal-default
  [{style :style :as p} & children]
  (let [style (merge style {:right 2
                            :bottom 2
                            :top 2
                            :border-radius 3})]
    (into [:div (assoc p :style style)]  children)))

(defn render-track-vertical-default
  [{style :style :as p} & children]
  (let [style (merge style {:right 2
                            :bottom 2
                            :top 2
                            :border-radius 3})]
    (into [:div (assoc p :style style)]  children)) )

(defn render-thumb-horizontal-default
  [{style :style :as p} & children]
  (let [style (merge style {:cursor "pointer"
                            :border-radius "inherit"
                            :background-color "rgba(0,0,0,0.2)"})]
    (into [:div (assoc p :style style)]  children) ))

(defn render-thumb-vertical-default
  [{style :style :as p} & children]
  (let [style (merge style {:cursor "pointer"
                            :border-radius "inherit"
                            :background-color "rgba(0,0,0,0.2)"})]
    (into [:div (assoc p :style style)]  children) ))
