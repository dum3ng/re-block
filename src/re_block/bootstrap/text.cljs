(ns re-block.bootstrap.text
  (:require [re-block.bootstrap.utils :as u]
            [re-block.utils :as uu]
            [clojure.string :as string]
            [reagent.core :as r]))

(defn -text
  [props & children]
  (let [[props children] (u/destruct props children)
        {:keys [tag class style color bg align
                transform no-wrap truncate
                weight italic]} props
        clss (uu/class
              class
              (u/prefix "text" {:color color
                                :align align
                                :transform transform
                                :nowrap no-wrap
                                :truncate truncate
                                })
              (u/prefix "bg" {:bg bg})
              (u/prefix "font-weight" {:weight weight})
              (if italic "font-italic"))
        attrs (dissoc props :tag :class :style :color :bg :align
                      :transform :no-wrap :truncate
                      :weight :italic)]
    (into [(keyword tag) (merge attrs
                                {:class clss
                                 :style (merge style
                                               (cond
                                                 (and truncate (= tag "span") ) {:display "inline-block"}
                                                 truncate {:display "inline"}
                                                 :else nil) )})]
          children)))

(def text 
  (u/make-view  -text
               :display "Text"
               :default-props {:color "black"
                               :bg "white"
                               :tag "p"}))


