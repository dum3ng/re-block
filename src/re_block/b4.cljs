(ns re-block.b4
  (:require [devcards.core :refer-macros [defcard]]
            [reagent.core :as r]
            [re-block.b4.button-group :refer [-button-group]]
            [re-block.b4.core :as b4]
            [re-block.b4.icon :as icon]))


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

(defcard button-demo
  (r/as-element [:div
                 [b4/button "default button"]
                 [:hr]
                 [b4/button {:bs-style "success"
                             :bs-size :large} "hello button"]
                 [:hr]
                 [b4/button {:href "#" :bs-style "info"}
                  [:span.text-dark "link button"]
                  [:span.text-danger " and danger "]]]))

(defcard button-group-demo
  (r/as-element [b4/button-group
                 [b4/button {:bs-style :primary} "primary"]
                 [b4/button {:bs-style :secondary} "second"]
                 [b4/button {:bs-style :primary} "primary"]]))

(defcard badge-demo
  (r/as-element [:div
                 [:h5 "button badge"]
                 [b4/button {:bs-style "info"
                             :bs-size :lg} "badge "
                  [b4/badge {:bs-style :secondary} 3]]
                 [:h5 "badges"]
                 [b4/badge {:bs-style "primary"}
                  "primary"]
                 [b4/badge {:bs-style "link"}
                  "link"]
                 [b4/badge {:bs-style "inverse"}
                  "inverse"]
                 [b4/badge {:bs-style "success"}
                  "success"]
                 [b4/badge {:bs-style "danger"}
                  "danger"]
                 [b4/badge {:bs-style "light"}
                  "light"]
                 [b4/badge {:bs-style "dark"}
                           "dark"]
                 ]))

(defcard breadcrumb-demo
  (r/as-element [b4/breadcrumb
                 [b4/breadcrumb-item {:active false} "three"]
                 [b4/breadcrumb-item {:active false} "two"]
                 [b4/breadcrumb-item {:active true} "one"]
                 ]))

(defcard card-demo
  (r/as-element [b4/card {:style {:width 200}}
                 [b4/card-image ]
                 [b4/card-body "card body"
                  [b4/card-title "card title"]
                  [b4/card-text "card text"]]]))

(defcard icon-demo
  (r/as-element [icon/iconic {:icon-name "account-login"}]))

(defcard checkbox-demo
  (r/as-element [
                 (fn []
                   (let [checked (r/atom true)]
                     (fn []
                       [b4/checkbox {:title "checkbox"
                                     :on-change #(swap! checked not)
                                     :checked @checked} ])))]))

(defcard grid-demo
  (r/as-element [:div
                 [b4/grid
                  [b4/row
                   [b4/col {:size 3
                            :style {:background-color "red"}} "col"]
                   [b4/col {:size 3
                            :sm 4 :lg 3 :align-self :flex-start
                            :style {:background-color "yellow"}} "col"]
                   [b4/col {:style {:background-color "green"}} "col"]]
                  [b4/row 
                   [b4/col {:size 3
                            :order :last
                            :style {:background-color "red"}} "I shoud be last"]
                   [b4/col {:size 3
                            :style {:background-color "yellow"}} "col"]
                   [b4/col {:size :auto :style {:background-color "green"}} "col"]]]
                 [:h3 "no gutters"]
                 [b4/row {:gutters false}
                  [b4/col "one"]
                  [b4/col "two"]
                  ]]))

(defcard list-group-demo
  (r/as-element [b4/list-group 
                 [:h2 "ahaha"]
                 [b4/list-group-item {:header "text header"}
                  "one"]
                 [b4/list-group-item {:header [icon/iconic {:icon-name "account-login"}]} "two"]
                 [b4/list-group-item "three"]]))

(defcard media-demo
  (r/as-element [b4/media
                 [b4/img-dev {:style {:width 50}}]
                 [b4/media-body "Aliquam erat volutpat.  Nunc eleifend leo vitae magna.  In id erat non orci commodo lobortis.  Proin neque massa, cursus ut, gravida ut, lobortis eget, lacus.  Sed diam.  Praesent fermentum tempor tellus.  Nullam tempus.  Mauris ac felis vel velit tristique imperdiet.  Donec at pede.  Etiam vel neque nec dui dignissim bibendum.  Vivamus id enim.  Phasellus neque orci, porta a, aliquet quis, semper a, massa.  Phasellus purus.  Pellentesque tristique imperdiet tortor.  Nam euismod tellus id erat.

"
                  [b4/media
                   [b4/img-dev {:style {:width 50}}]
                   [b4/media-body "Aliquam erat volutpat.  Nunc eleifend leo vitae magna.  In id erat non orci commodo lobortis.  Proin neque massa, cursus ut, gravida ut, lobortis eget, lacus.  Sed diam.  Praesent fermentum tempor tellus.  Nullam tempus.  Mauris ac felis vel velit tristique imperdiet.  Donec at pede.  Etiam vel neque nec dui dignissim bibendum.  Vivamus id enim.  Phasellus neque orci, porta a, aliquet quis, semper a, massa.  Phasellus purus.  Pellentesque tristique imperdiet tortor.  Nam euismod tellus id erat.

"]]]]))

(defcard progressbar-demo
  (r/as-element [b4/progressbar {:now 20
                                 :striped true
                                 :label "20%"}]))

(defcard radio-demo
  (r/as-element [b4/radio {} [:span.text-danger.px-1 "I have a tooltip"]]))

(defcard tooltip-demo
  (r/as-element [b4/tooltip {:class "in"} "tooltip"]))
