(ns re-block.button
  (:require [devcards.core :refer-macros [defcard]]
            [reagent.core :as r]))

(defn button
  []
  [:button "button"])


(defcard button-view
  (r/as-element [button]))
