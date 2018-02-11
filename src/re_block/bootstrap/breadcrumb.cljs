(ns re-block.bootstrap.breadcrumb
  (:require [re-block.bootstrap.utils :as u]
            [re-block.utils :as uu]))

(defn -breadcrumb
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class  tag]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              "breadcrumb"
              class)]
    (into [(keyword tag) (merge attrs
                                {:class clss})]
          children)))

(def breadcrumb
  (u/make-view -breadcrumb
               :display-name "Breadcrumb"
               :default-props {:tag "ol"}))

;; MARK breadcrumb item
(defn -breadcrumb-item
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class active tag]} props
        attrs (dissoc props :class :active :tag)
        clss (uu/class
              "breadcrumb-item"
             (if active "active") 
              class)]
    (into [(keyword tag) (merge attrs
                                {:class clss})]
          children)))

(def breadcrumb-item
  (u/make-view -breadcrumb-item
               :display-name "BreadcrumbItem"
               :default-props {:tag "li"}))

