(ns re-block.b4.grid
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]
            [clojure.string :as string]))

(declare grid row col)

(defn -grid
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [fluid tag class]
         :or {fluid false tag "div"}} props
        props (dissoc props :fluid :tag :class)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class (u/prefix bsp (if fluid "fluid")))]
    (if (fn? tag)
      (apply tag  (assoc eps :class clss) children)
      (into [(keyword tag) (assoc eps :class clss)]
            children)) ))

(def grid
  (u/bs-class "container" -grid))

(defn prefix-align
  ([s p]
   (if (or (nil? p) (string/blank? p))
     ""
     (str (name s) "-" (name p))))
  ([s p & sps]
   (str (prefix-align s p) " "
        (apply prefix-align sps))))

(defn  -row
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [gutters align-items justify-content class]
         :or {gutters true}} props
        props (dissoc props :gutters :align-items :justify-content :class)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class (u/get-class-set bsp)
                       (prefix-align :align-items align-items
                                    :justify-content justify-content)
                       (if (not gutters) "no-gutters" )
                       class)
        ]
    (into [:div (assoc eps :class clss) ]
          children)))

(def row 
  (u/bs-class " row" -row))

(defn- parse-flex
  [f]
  (if (number? f)
    f (name f)))
(defn prefix-flex
  [props p]
  (let [psize (if (:size props) (str p "-" (:size props))
                  "")
        o (if (:order props) (str "order-" (parse-flex (:order props)) ))
        ps (reduce #(str %1 " " (if (%2 props)
                                  (str p "-" (name %2) "-" (parse-flex (%2 props))))
                         (let [k (keyword (str "offset" "-" (name %2)))
                               offset (props k )]
                           (if offset
                             (str " " (name k) "-" offset))))
                   "" [:xs :sm :md :lg :xl])]
    (str psize " " o " " ps)))

(defn -col
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [align-self class]} props
        cls (prefix-flex props "col")
        props (dissoc props :size :xs :sm :md :lg :xl
                      :offset-xs :offset-sm :offset-lg :offset-xl
                      :order :align-self :class)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              (u/get-class-set bsp)
              cls
              (prefix-align :align-self align-self)
              class )]
    (into [:div (assoc eps :class clss) ] children) ))

(def col
  (u/bs-class "col" -col))

