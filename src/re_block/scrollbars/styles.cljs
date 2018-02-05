(ns re-block.scrollbars.styles)

(def container-style-default
  {:position "relative"
   :overflow "hidden"
   :width "100%"
   :height "100%"})

(def container-style-auto-height
  {:height "auto"})

(def view-style-default
  {:position "absolute"
   :top 0
   :left 0
   :right 0
   :bottom 0
   :overflow "scroll"
   :WebkitOverflowScrolling "touch"})

(def view-style-auto-height
  {:position "relative"
   :top nil
   :left nil
   :right nil
   :bottom nil })

(def view-style-universal-initial
  {:overflow "hidden"
   :margin-right 0
   :margin-bottom 0})

(def track-horizontal-style-default
  {:position "absolute"
   :height "6px"})

(def track-vertical-style-default
  {:position "absolute"
   :width  "6px"})

(def thumb-horizontal-style-default
  {:position "relative"
   :display "block"
   :height "100%"})

(def thumb-vertical-style-default
  {:position "relative"
   :display "block"
   :width "100%"})

(def disable-select-style
  {:user-select "none"})
