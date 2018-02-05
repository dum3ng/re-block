(ns re-block.scrollbars.utils
  (:require [re-block.utils :as u]))

(defn get-inner-height
  [el]
  (let [client-height (.-clientHeight el)
        s (js/getComputedStyle el)
        padding-top (. s -paddingTop)
        padding-bottom (. s -paddingBottom)]
    (- client-height (js/parseFloat padding-top) (js/parseFloat padding-bottom))))


(defn get-inner-width
  [el]
  (let [client-width (.-clientWidth el)
        s (js/getComputedStyle el)
        padding-left (. s -paddingLeft)
        padding-right (. s -paddingRight)]
    (- client-width (js/parseFloat padding-left) (js/parseFloat padding-right))))

(defonce scrollbar-width (atom false))

;; from https://www.npmjs.com/package/dom-css 
(def number-tags
  #{:top, :right, :bottom, :left, 
    :width, :height, :font-size, 
    :padding-left, :padding-right, 
    :padding-top, :padding-bottom, 
    :margin-left, :margin-right, 
    :margin-top, :margin-bottom, 
    :padding, :margin, :perspective} )

(defn suffix-px
  [k v]
  (if (and (number? v) (number-tags k))
    (str v "px")
    v))
;; (suffix-px :top 10)
(defn css
  [el style]
  (reduce-kv (fn [p k v]
               (doto p 
                   (aset "style" (u/dash->camel k) (suffix-px k v)))) el style))

(defn get-scrollbar-width
  []
  (cond
    (not= false @scrollbar-width ) @scrollbar-width
    :else (do
            (if (nil? js/document)
               (reset! scrollbar-width 0)
               (let [div (js/document.createElement "div")
                    div  (css div {:width "100px"
                               :height "100px"
                               :position "absolute"
                               :top "-99999px"
                               :overflow "scroll"
                               :MsOverflowStyle "scrollbar"})]
                 (js/document.body.appendChild div)
                 (reset! scrollbar-width
                         (- (.-offsetWidth div)
                            (.-clientWidth div)))
                 (js/document.body.removeChild div)))
            (or @scrollbar-width 0))))
