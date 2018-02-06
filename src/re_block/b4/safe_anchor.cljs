(ns re-block.b4.safe-anchor
  (:require
   [reagent.core :as r]
   [re-block.utils :as uu]
   [re-block.b4.utils :as u]
   [reagent.core :as r]
   [clojure.string :as string]))

(defn- trivial-href?
  [h]
  (or (not h) (= "#" (string/trim h))))

(defn- handle-click
  [c e]
  (let [{:keys [on-click href disabled]} (r/props c)]
    (if (and disabled (trivial-href? href))
      (.preventDefault e))
    (if disabled
      (.stopPropagation e)
      ( if on-click
       (on-click e))) ))

(defn- handle-key-down
  [c e]
  (if (= " " (.-key e))
    (do (.preventDefault e)
        (handle-click c e))))

;; use `tag` instead of `ComponentClass`
;; tag can be a string/keyword for html tag, or
;; a function for reagent function.
(defn -safe-anchor
  [props & children ]
  (let [this (r/current-component)
        [props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [tag disabled on-key-down ] :or {tag "a"}} props
        props (-> props
                  (dissoc :tag :disabled :on-key-down)
                  (uu/assoc-if (trivial-href? (:href props) )
                               :role (or (:role props) "button")
                               :href (or (:href props) "#"))
                  (uu/assoc-if disabled
                               :tab-index -1
                               :style (merge {:pointer-events "none"} (:style props)))
                  (merge  {:on-click #(handle-click this %)
                           :on-key-down #(do
                                           (handle-key-down this %)
                                           (on-key-down %))}))]
    (if (fn? tag)
      (apply tag  props children)
      (into [(keyword tag) props] children))))

(def safe-anchor  -safe-anchor)

