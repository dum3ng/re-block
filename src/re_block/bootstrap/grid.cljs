(ns re-block.bootstrap.grid
  (:require [re-block.bootstrap.utils :as u]
            [clojure.string :as string]
            [re-block.utils :as uu]))


(defn -grid
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [tag fluid class ]} props
        attrs (dissoc props :tag :fluid)
        clss (uu/class
              (if fluid "container-fluid" "container")
              class)]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))


(def grid
  (u/make-view -grid
               :display-name "Grid"
               :default-props {:tag "div"
                               :gutters true}))

;; MARK row

(defn -row
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [class gutters tag]} props
        attrs (dissoc props  :gutters :tag)
        clss (uu/class
              "row"
              (if (not gutters) "no-gutters" )
              class)]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))


(def row
  (u/make-view -row
               :display-name "Row"
               :default-props {:tag "div"
                               :gutters true}))

;; MARK col
(defn- build
  ([s [k v]]
   (build s k v))
  ([s k v]
   (if v
     (if (string/blank? s)
       (str (name k ) "-" v)
       (str s "-" (name k) "-" v)))))

(defn col-class
  [props]
  (let [s (select-keys props [:sm :md :lg :xl])
        off (select-keys props [:offset-sm :offset-md :offset-lg :offset-xl])
        order (:order props)
        size (or (:size props) (:xs props))
        off-size (or (:offset props) (:offset-xs props))]
    (uu/class
     "col"
     (u/prefix "col" size )
     (u/prefix "offset" off-size )
     (u/prefix "order" order) 
     (mapv (partial build "col") s)
     (mapv (partial build "") off))))

(defn -col
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [tag class]} props
        attrs (dissoc props :tag :xs :sm :md :lg :xs :size
                      :offset :offset-xs :offset-sm :offset-md :offset-lg :offset-xs
                      :order )
        clss (uu/class
              (col-class props)
              class)]
    (into [(keyword tag) (assoc attrs :class clss)]
          children)))


(def col
  (u/make-view -col
               :display-name "Col"
               :default-props {:tag "div"
                               :gutters true}))


