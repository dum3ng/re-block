(ns re-block.bootstrap.button
  (:require [re-block.bootstrap.utils :as u]
            [re-block.utils :as uu]
            [reagent.core :as r]))


(defn -button
  [props & children]
  (let [this (r/current-component)
          [props children] (u/destruct props children)
          {:keys [tag active block class
                  color outline size inner-ref]} props
          attrs (dissoc props :tag :active :block :class
                        :color :outline :size :inner-ref)
          tag (if (:href props) "a" tag)
          clss (uu/class
                class
                "btn"
                (u/prefix "btn" {:outline outline
                                 :size size
                                 :color (and (not outline)
                                             color)
                                 :block block})
                {:active (:disabled props)
                 :disabled (:disabled props)})]
    (into [(keyword tag) (merge attrs
                                {:type (if (and (= tag "button")
                                                (:on-click attrs))
                                         "button")
                                 :class clss
                                 :ref inner-ref
                                 :on-click  (partial (aget this "onClick") this)
                                 })]
          children)))


(def button
  (u/make-view  -button
               :display-name "Button"
               :default-props {:tag "button"
                               :color "secondary"}
               :meta {:onClick (fn [c e]
                                 (let [ps (r/props c)]
                                   (if (:disabled ps)
                                     (.preventDefault e)
                                     (if-let [handler  (:on-click ps)]
                                       (handler e)))))}))



;; MARK button group
(defn -button-group
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class size vertical tag]} props
        attrs (dissoc props :class :size :vertical :tag)
        clss (uu/class
              class
              (u/prefix "btn-group" {:size size})
              (if vertical "btn-group-vertical" "btn-group"))]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))

(def button-group
  (u/make-view -button-group
               :display-name "ButtonGroup"
               :default-props {:tag "div"
                               :role "group"}))

;; MARK button toolbar
(defn -button-toolbar
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class  tag]} props
        attrs (dissoc props :class  :tag)
        clss (uu/class
              class
             "btn-toolbar")]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))

(def button-toolbar
  (u/make-view -button-toolbar
               :display-name "ButtonToolbar"
               :default-props {:tag "div"
                               :role "toolbar"}))
