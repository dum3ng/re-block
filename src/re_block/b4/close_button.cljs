(ns re-block.b4.close-button
  (:require [goog.string :as gs]))

(defn close-button
  [{:keys [on-click label] :or {label "Close"}}]
  [:button.close {:type "button"
                  :on-click on-click}
   [:span {:aria-hidden true} (gs/unescapeEntities "&times;")]
   [:span.sr-only label]])
