(ns re-block.b4
  (:require [devcards.core :refer-macros [defcard]]
            [reagent.core :as r]
            [re-block.b4.core :as b4]))


(defcard x
  (let [tag :h1]
    (r/as-element [:h1.btn.btn-warning "error"])))

(defcard close-button
  (r/as-element [b4/close-button]))

(defn -alert-demo
  []
  (let [show (r/atom true)]
    (fn []
      [b4/card
       [b4/card-body
        [b4/button {:on-click #(swap! show not)} "toggle"]
        [b4/alert {:bs-size :large
                   :bs-style :success
                   :style {:display (if @show "inherit" "none")}
                   :on-dismiss #(do (print "dismiss")(swap! show not)) }
         "message"]]])))
(defcard alert-demo
  (r/as-element [-alert-demo] ))

(defcard button-view
  (r/as-element [:div
                 [b4/button "default button"]
                 [:hr]
                 [b4/button {:type "success"} "hello button"]
                 [:hr]
                 [b4/button {:href "#" :type "info"}
                  [:span.text-light "link button"]]]))


(defcard card-demo
  (r/as-element [b4/card {:style {:width 200}}
                 [b4/card-image ]
                 [b4/card-body "card body"
                  [b4/card-title "card title"]
                  [b4/card-text "card text"]]]))
