(ns re-block.b4.button
  (:require [re-block.b4.utils :as u]))

(defn button
  ([c]
   (button {} c))
  ([props & children]
   (let [t (or (:type props ) "primary")
         props (u/update-props-class props (str "btn-" (name t)))]
     (if (:href props)
       (into [:a.btn props] children)
       (into   [:button.btn.btn-primary props]
               children)))))

