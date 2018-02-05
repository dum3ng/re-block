(ns re-block.scrollbars
  (:require-macros
   [devcards.core :refer [defcard ]
    ]
   [reagent.core :as r])
  (:require [re-block.scrollbars.core :refer [scrollbars]]
            [reagent.core :as r]))


(defcard scrollbars-demo
  (r/as-element [scrollbars {:style {:width 100
                                     :height 100}}
                 [:div {:style {:background "gray"
                               :height 300}}
                  "hello ..."]]))
