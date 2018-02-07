(ns re-block.b4.text)

#_(props {:inline boolean
          })

(ns re-block.b4.text
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn -text
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [] } props
        props (dissoc props )
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              )]
    ))

(def text
  (u/bs-style :default-active-index  -text))

