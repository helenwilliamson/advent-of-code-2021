(ns advent-of-code-2021.puzzle-1
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-1"))
  ([name]
   (->> (slurp (str "resources/" name))
        (str/split-lines)
        (map #(Integer/parseInt %1)))))

(defn number-increases
  [data]
  (->> data
       (partition 2 1)
       (map #(- (second %1) (first %1)))
       (filter pos?)
       count))

(defn sliding-windows
  [data]
  (->> data
       (partition 3 1)
       (map #(reduce + %1))))

(defn version-1
  []
  (->> (read-input)
       (number-increases)))

(defn version-2
  []
  (->> (read-input)
       (sliding-windows)
       (number-increases)))
