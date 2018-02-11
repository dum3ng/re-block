(ns re-block.bootstrap.fade
  (:require [re-block.bootstrap.utils :as u]))


(defn -fade
  [props & children]
  (let [[props children] (u/destruct props children)
        ]))


(def fade
  (u/make-view -fade
               :display-name "Fade"
               :default-props {:tag "div"
                               :base-class "fade"
                               :base-class-active "show"
                               :timeout 600
                               :appear true
                               :enter true
                               :exit true
                               :in true}))

