(ns re-block.bootstrap.list-group
  (:require [re-block.bootstrap.utils :as u]
            [re-block.utils :as uu]
            [reagent.core :as r]))

(defn -list-group
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        _flush (:flush props)
        attrs (dissoc props :class :tag :flush)
        clss (uu/class
              class
              "list-group"
              {:list-group-flush flush}) ]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))


(def list-group
  (u/make-view -list-group
               :display-name "ListGroup"
               :default-props {:tag "ul"}))

;; MARK list group item

(defn -list-group-item
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag active disabled action color]} props
        attrs (dissoc props :class :tag :active :disabled :action :color)
        clss (uu/class
              "list-group-item"
              {:active active
               :disabled disabled
               :list-group-item-action action}
              (u/prefix "list-group-item" {:color color})
              class)
        attrs (if disabled (assoc attrs :on-click #(.preventDefault %))
                  attrs)]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))


(def list-group-item
  (u/make-view -list-group-item
               :display-name "ListGroupItem"
               :default-props {:tag "li"}))


;; MARK list group item text

(defn -list-group-item-text
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag )
        clss (uu/class
              "list-group-item-text"
              class)]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))


(def list-group-item-text
  (u/make-view -list-group-item-text
               :display-name "ListGroupItemText"
               :default-props {:tag "p"}))


;; MARK list group item-heading

(defn -list-group-item-heading
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag )
        clss (uu/class
              "list-group-item-heading"
              class)]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))


(def list-group-item-heading
  (u/make-view -list-group-item-heading
               :display-name "ListGroupItemHeading"
               :default-props {:tag "h5"}))




