(ns re-block.bootstrap.badge
  (:require [re-block.bootstrap.utils :as u]
            [re-block.utils :as uu]))


(defn -badge
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class color pill tag]} props
        attrs (dissoc props :class :color :pill :tag)
        clss (uu/class
              class
              "badge"
              (u/prefix "badge" {:color color
                         :pill pill}))
        tag (if (and (= tag "span") (:href attrs))
              "a" tag)]
    (into [(keyword tag) (merge attrs
                                {:class clss})]
          children)))

(def badge
  (u/make-view -badge
               :display-name "Badge"
               :default-props {:tag "span"
                               :color "secondary"
                               :pill false}))
