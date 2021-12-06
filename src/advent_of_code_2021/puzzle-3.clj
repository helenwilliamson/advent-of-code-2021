(ns advent-of-code:puzzle-3
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-3"))
  ([name]
   (->> (slurp (str "resources/" name))
        (str/split-lines))))

(defn calculate-gamma
  [data]
  (let [size (count (first data))
        half-way (/ (count data) 2)]
    (loop [i 0
           number ""]
      (if (= i size)
        number
        (let [zeros (->> (filter #(= \0 (get %1 i)) data)
                         (count)
                         (#(> %1 half-way)))
              bit (if zeros \0 \1)]
          (recur (inc i) (str number bit)))))))

(defn calculate-epsilon
  [gamma]
  (str/join (map #(if (= \0 %1) \1 \0) gamma)))

(defn calculate-rates
  [data]
  (let [gamma (calculate-gamma data)]
    (vector gamma (calculate-epsilon gamma))))

(defn calculate-oxygen-co2
  [comparator data]
  (loop [values data
         i 0]
    (let [half-way (/ (count values) 2)]
      (if (= 1 (count values))
        (first values)
        (let [zeros (->> (filter #(= \0 (get %1 i)) values)
                         (count)
                         (#(comparator %1 half-way)))
              bit (if zeros \0 \1)]
          (recur (filter #(= bit (get %1 i)) values) (inc i)))))))

(defn version-1
  []
  (->> (read-input)
       (calculate-rates)
       (map #(Integer/parseInt %1 2))
       (reduce *)))

(defn version-2
  []
  (->> (read-input)
       ((fn [data] (vector (calculate-oxygen-co2 > data) (calculate-oxygen-co2 <= data))))
       (map #(Integer/parseInt %1 2))
       (reduce *)))
