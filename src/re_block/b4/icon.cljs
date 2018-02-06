(ns re-block.b4.icon
  (:require [re-block.utils :as uu]
            [clojure.string :as string]))

(defn iconic
  [props]
  (let [{icon-name :icon-name
         class :class
         style :style} props]
    [:span.oi {:class (uu/class (str "oi-" icon-name)
                                class)
               :style style
               :title (string/replace icon-name "-" " ")
               :aria-hidden true}]))
