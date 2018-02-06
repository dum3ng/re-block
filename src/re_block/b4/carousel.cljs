(ns re-block.b4.carousel
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]
            [reagent.core :as r]))
;; TODO
(defn -carousel
  []
  (r/create-class
   {:get-initial-state
    (fn [this]
      (let [props (r/props this)
            dai (:default-active-index props)]
        {:active-index (if (nil? dai) 0 dai)
         :previous-active-index nil
         :direction nil}))
    :component-did-mount
    (fn [this]
      (.waitForNext this this))

    :component-will-receive-props
    (fn [this argv]
      (let [[_ np & children] argv
            [np children] (if (map? np) [np children]
                              [{} (into [np] children)])
            active-index (.getActiveIndex this this)]
        (if (and (-> np  :active-index nil? not)
                 (-> np :active-index (= active-index) not))
          (do
            (js/clearTimeout (.-timeout this))
            (r/set-state this {:previous-active-index active-index
                          :direction (if (-> np :direction nil? )
                                       (.getDirection this this active-index (:active-index np))
                                       (:direction np))})))
        (if (and (nil? (:active-index np))
                 (>= (:active-index (r/state this))
                     (count children)))
          (r/set-state this
                       {:active-index 0
                        :previous-active-index nil
                        :direction nil}))))

    :component-will-unmount
    (fn [this]
      (js/clearTimeout (.-timeout this))
      (aset this "isUnmounted" true))

    :getActiveIndex
    (fn [c]
      (let [ai (:active-index (r/props c))]
        (if (nil? ai)
          (-> c r/state :active-index)
          ai)))

    :getDirection
    (fn [c pi i]
      (if (= pi i)
        nil
        (if (> pi i)
          "prev" "next")))

    :handleItemAnimateOutEnd
    (fn [c]
      )
    :render
    (fn [this]
      [:div ])
    }))

(def carousel
  (u/bs-class "carousel" -carousel))

