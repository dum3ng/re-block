(ns re-block.core
  (:require
   #_[om.core :as om :include-macros true]
   [sablono.core :as sab :include-macros true]
   [reagent.core :as r]
   [re-block.utils :as u]
   [clojure.string :as string]
   [re-block.scrollbars]
   [re-block.button]
   [re-block.scrollbars.core :refer [scrollbars]])
  (:require-macros
   [devcards.core :as dc :refer [defcard deftest]]
   ))

(enable-console-print!)

(defcard first-card
  (sab/html [:div
             [:h1 "This is your first devcard!"]]))

(defcard second
  (sab/html [:h2 "second"]))


(defn- rgba
  []
  (str "rgba(" (string/join ","
                (repeatedly 3 #(rand-int 256)) )  ",255)"))
(defn handler [e]
  (print "handler: " (. e -target)))

(defn demo
  []
  (r/create-class
   {:helloWorld (fn [] (print "hello world!"))
    :change
    (fn [e]
      (print "change color")
      (this-as c
        (aset c "view" "style" "color" (rgba))))
    :hello
    (fn [e]
      (this-as c
        (print "hello this is : " e)
        (print "current :" c)))
    :component-did-mount
    (fn [this]
      ;; (aset )
      (.addEventListener (. this -view)
                         "click" #(handler %))
      (.addEventListener (. this -button)
                         "click" (. this -change)))
    :render
    (fn [this ]
      (print "current comp in render: " (r/current-component) )
      [:div 
       [:h2 {:ref #(aset this "view" %)
             :style {:background "yellow"}}"I am the view"]
       [:button {:on-click (aget this "helloWorld")} "hello"]
       [:button {:ref #(aset this "button" %)}
        "change color"]
       [:button {:on-click (fn [e](.removeEventListener (. this -button) "click" (aget  this "change")))}
        "remove listener"]])}))

(defn real-demo
  []
  (u/autobind (demo) :hello :change :helloWorld)
  #_(let [c (demo)
        p (aget c "prototype")
        hello (aget p "hello")
        change (aget p "change")
        pairs (aget p "__reactAutoBindPairs")
        paris (doto pairs
                (.push "hello" hello)
                (.push "change" change))]

    ;; (js/console.log "hello : %o" hello)
    ;; (js/console.log "proto : %o" p)
      c)
  )
(defn reag
  [p h]
  (r/create-class
   {:component-did-mount
    (fn [this]
      (print "mounted!")
      (print (r/props this))
      (print (r/children this)
             )
      (print (r/argv this)))
    :render
    (fn [this]
      (let [ps (r/props this)]
        [:div
         [:h2 "render"]
         [:h3 "is " ps]]))
    ;; :reagent-render
    ;; (fn [p h]
    ;;   (let [pp (r/props (r/current-component))]
    ;;     (print "props is " pp))
    ;;   [:div
    ;;    [:h1 (str "a is " (:a p) )]
    ;;    h])
    })
  )
(defcard three
  (r/as-element [reag {:a 4} [:h2 "children h2"]  ]))

(defcard demo-card
  (r/as-element [real-demo]))



(defn app
  []
  [:div
   [:h1 "hello"]
   [scrollbars {:style {:width 100 :height 100}}
    [:p {:style {:background-color "yellow"
                 :height 300}}"somthing long"]]])

(defn main []
  ;; conditionally start the app based on whether the #main-app-area
  ;; node is on the page
  (if-let [node (.getElementById js/document "main-app-area")]
    (.render js/ReactDOM (sab/html [:div "This is working"]) node)))

(main)

;; (defn init
;;   []
;;   (r/render [app] (.getElementById js/document "main-app-area")))

;; (init)
;; remember to run lein figwheel and then browse to
;; http://localhost:3449/cards.html
