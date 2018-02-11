(ns re-block.bootstrap
  (:require [reagent.core :as r]
            [re-block.bootstrap.core :as b])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]]
   ))


(defcard bootstrap
  (r/as-element [:h1 "bootstrap v4"]))

(defcard badge-demo
  (r/as-element [:div
                 [:h1 "badges"]
                 [b/badge {:class "mr-3 "  :tag "a"} 3]
                 [b/badge {:class "mr-3 " :color "success"
                           } 0]
                 [b/badge {:color "success"
                           :class "mr-3"}]]))

(defcard breadcrumb-demo
  (r/as-element [:div
                 [b/breadcrumb
                  [b/breadcrumb-item "foo"]
                  [b/breadcrumb-item {:active true} "bar"]
                  [b/breadcrumb-item "baz"]]]))
(defcard button-demo
  (r/as-element [:div
                 [:h5 "primary lg button"]
                 [b/button {:color "primary"
                            :outline true
                            :size :lg
                            :on-click #(js/alert "should here")
                            } "primary large"]
                 [b/button {:color "success"
                            :class "text-dark"
                            :on-click #(js/alert "click should not appear")
                            :disabled true} "primary large"]
                 [:h5 "button toolbar and group"]
                 [b/button-toolbar
                  [b/button-group {:class "mr-2"}
                   [b/button {:color "primary"} "one"]
                   [b/button {:color "success"} "one"]
                   [b/button "one"]]
                  [b/button  {
                              :class "mr-2"} "two"]
                  [b/button {:class "mr-2"} "three"]
                  [b/button "one"]]
                 ]))


(defcard card-demo
  (r/as-element [b/card {:style {:width 200}}
                 [b/card-img {:top true
                              :src "/img/placeholder.png"}]
                 [b/card-body
                  [b/card-title "Curabitur lacinia pulvinar nibh.
"]
                  [b/card-text "Pellentesque dapibus suscipit ligula.  Donec posuere augue in quam.  Etiam vel tortor sodales tellus ultricies commodo.  Suspendisse potenti.  "]                 ]])) 


(defcard list-group-demo
  (r/as-element [b/list-group
                 
                 [b/list-group-item {:color "info"
                                     :active true}
                  [b/list-group-item-heading "heading"]
                  [b/icon {:icon-name "aperture"
                           :class "mr-2"}]
                  "item 1"]
                 
                 [b/list-group-item
                  [b/list-group-item-heading "heading"]
                  [b/icon {:icon-name "book"
                           :class "mr-2 text-info"}]
                  "item book"]                ]))

(defcard text-demo
  (r/as-element [b/text {:bg "info"
                         :color "light"
                         :weight "bold"
                         :truncate true}
                 "Nullam eu ante vel est convallis dignissim.  Fusce suscipit, wisi nec facilisis facilisis, est dui fermentum leo, quis tempor ligula erat quis odio.  Nunc porta vulputate tellus.  Nunc rutrum turpis sed pede.  Sed bibendum.  Aliquam posuere.  Nunc aliquet, augue nec adipiscing interdum, lacus tellus malesuada massa, quis varius mi purus non odio.  Pellentesque condimentum, magna ut suscipit hendrerit, ipsum augue ornare nulla, non luctus diam neque sit amet urna.  Curabitur vulputate vestibulum lorem.  Fusce sagittis, libero non molestie mollis, magna orci ultrices dolor, at vulputate neque nulla lacinia eros.  Sed id ligula quis est convallis tempor.  Curabitur lacinia pulvinar nibh.  Nam a sapien.

"]))

(defcard grid-demo
  (r/as-element [b/grid {:class "bg-warning"}
                 [b/row {:gutters false}
                  [b/col {:size 3 :class "bg-primary"} "one"]
                  [b/col {:size 5 :order "first" :class "bg-success"} "no gutters"]
                  [b/col {:size 4 :class "bg-info"} "info"]]
                 [b/row
                  [b/col {:size 3 :class "bg-primary"} "one"]
                  [b/col {:size "auto" :order "last" :class "bg-success"} "last"]
                  [b/col {:size 4 :class "bg-info"} "info"]]
                 ]))
