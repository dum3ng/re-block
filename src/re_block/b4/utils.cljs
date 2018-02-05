(ns re-block.b4.utils)

(defn update-props-class
  [props cls]
  (update props :class
          #(if (nil? %)
             cls
             (str cls " " %))))
