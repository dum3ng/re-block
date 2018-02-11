(ns re-block.bootstrap.icon
  (:require [re-block.bootstrap.utils :as u]
            [re-block.utils :as uu]
            [clojure.string :as string]
            [reagent.core :as r]))


(defn -icon-iconic
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [icon-name class ]} props
        attrs (dissoc props :icon-name :class )]
    [:span.oi (merge  {:class (uu/class class
                                             (str "oi-" icon-name))
                            :title (string/replace icon-name "-" " ")
                            :aria-hidden true} attrs)]))

(defn -icon-octicons
  [props & children]
  [:span "not impletented yet."])

(defn -icon
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [icon-set]} props
        props (dissoc props :icon-set)]
    (case (keyword icon-set)
      :iconic (apply -icon-iconic props children)
      :octicons (apply -icon-octicons props children))))

(def icon
  (u/make-view -icon
               :display "Icon"
               :default-props {:icon-set "iconic"}))

