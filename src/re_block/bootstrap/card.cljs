(ns re-block.bootstrap.card
  (:require [re-block.bootstrap.utils :as u]
            [re-block.utils :as uu]
            [reagent.core :as r]))


(defn -card
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class color block tag
                body inverse outline]} props
        attrs (dissoc props :class :color :block :tag
                      :body :inverse :outline)
        clss (uu/class
              class
              "card"
              {:text-white inverse
               :card-body (or block body)}
              (u/prefix (if outline "border" "bg")
                        {:color color}))]
    (into [(keyword tag) (merge attrs
                                {:class clss})]
          children)))

(def card
  (u/make-view -card
               :display-name "Card"
               :default-props {:tag "div"}))


;; MARK card img
(defn -card-img
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag top bottom ]} props
        attrs (dissoc props :class :tag :top :bottom)
        clss (uu/class
              class
              (cond
                top "card-img-top"
                bottom "card-img-bottom"
                :else "card-img"))]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-img
  (u/make-view -card-img
               :display-name "CardImg"
               :default-props {:tag "img"}))


;; MARK card header
(defn -card-header
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-header")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-header
  (u/make-view -card-header
               :display-name "CardHeader"
               :default-props {:tag "div"}))


;; MARK card body
(defn -card-body
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-body")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-body
  (u/make-view -card-body
               :display-name "CardBody"
               :default-props {:tag "div"}))


;; MARK card title
(defn -card-title
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-title")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-title
  (u/make-view -card-title
               :display-name "CardTitle"
               :default-props {:tag "h5"}))

;; MARK card text
(defn -card-text
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-text")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-text
  (u/make-view -card-text 
               :display-name "CardText"
               :default-props {:tag "p"}))


;; MARK card columns
(defn -card-columns
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-columns")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-columns
  (u/make-view -card-columns
               :display-name "CardColumns"
               :default-props {:tag "div"}))


;; MARK card deck
(defn -card-deck
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-deck")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-deck
  (u/make-view -card-deck
               :display-name "CardDeck"
               :default-props {:tag "div"}))


;; MARK card footer
(defn -card-footer
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-footer")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-footer
  (u/make-view -card-footer
               :display-name "CardFooter"
               :default-props {:tag "div"}))


;; MARK card group
(defn -card-group
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-group")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-group
  (u/make-view -card-group
               :display-name "CardGroup"
               :default-props {:tag "div"}))


;; MARK card img overlay
(defn -card-img-overlay
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag ]} props
        attrs (dissoc props :class :tag)
        clss (uu/class
              class
              "card-img-overlay")]
    (into [(keyword tag)
           (assoc attrs :class clss)]
          children)))

(def card-img-overlay
  (u/make-view -card-img-overlay
               :display-name "CardImgOverlay"
               :default-props {:tag "div"}))



;; MARK card link
(defn -card-link
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag inner-ref]} props
        attrs (dissoc props :class :inner-ref :tag)
        clss (uu/class
              class
              "card-link")]
    (into [(keyword tag)
           (assoc attrs :class clss :ref inner-ref)]
          children)))

(def card-link
  (u/make-view -card-link
               :display-name "CardLink"
               :default-props {:tag "a"}))



;; MARK card subtitle
(defn -card-subtitle
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class tag inner-ref ]} props
        attrs (dissoc props :inner-ref :class :tag)
        clss (uu/class
              class
              "card-subtitle")]
    (into [(keyword tag)
           (assoc attrs :class clss :ref inner-ref)]
          children)))

(def card-subtitle
  (u/make-view -card-subtitle
               :display-name "CardSubtitle"
               :default-props {:tag "h6"}))


