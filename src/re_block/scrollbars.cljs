(ns re-block.scrollbars
  (:require-macros
   [devcards.core :refer [defcard ]
    ]
   [reagent.core :as r])
  (:require [re-block.scrollbars.core :refer [scrollbars]]
            [reagent.core :as r]))

(defn render-thumb-vertical
  [{style :style :as p} & children]
  (let [style (merge style {:cursor "pointer"
                            :border-radius "inherit"
                            :background-color "rgba(0,0,0,0.2)"})]
    (into [:div (assoc p :style style)]  children) ))

(defcard scrollbars-demo
  (r/as-element [scrollbars {:style {:width 100
                                     :height 100}}
                 [:div {:style {:background "gray"
                               :height 300}}
                  "hello ..."]]))

(defcard scrollbars-horizontal-demo
  (r/as-element [scrollbars {:style {:width 100
                                     :height 100}}
                 [:div {:style {:background "gray"
                                :width 300
                                :height 300}}
                  "horizontal and vertical ..."]]))
