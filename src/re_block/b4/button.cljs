(ns re-block.b4.button
  (:require [re-block.b4.utils :as u]
            [re-block.utils :as uu]
            [re-block.b4.safe-anchor :refer [safe-anchor]]))

(defn -button
  [props & children]
   (let [[props children] (if (map? props)
                            [props children]
                            [{} (into [props]  children)])
         {:keys [tag active block class] :or {active false
                                              block false
                                              tag :button
                                          disabled false}} props
         props (dissoc props :active :block :class)
         [bsp esp] (u/split-bs-props props)
         clss (uu/class
               class
               (u/get-class-set bsp)
               {:active active
                (u/prefix bsp "block" ) block})]
     (if (:href esp)
       (into [safe-anchor (merge esp
                                 {:class (uu/class
                                          clss
                                         (if (:disabled esp) "disabled") )})]
             children)
       (let [ps (merge esp
                       {:type (or (:type esp) "button")
                        :class clss})]
         (if (fn? tag)
                (apply tag ps children)
                (into   [(keyword tag) ps]
                        children))))))

(def button
  (with-meta  (u/bs-class :btn (u/bs-style :default -button))
    {:display-name "Button"}))
