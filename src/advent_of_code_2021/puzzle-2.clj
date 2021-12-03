(ns advent-of-code-2021.puzzle-2
  (:gen-class)
  (:require [clojure.string :as str]))

(defn read-input
  ([] (read-input "puzzle-2"))
  ([name]
   (->> (slurp (str "resources/" name))
        (str/split-lines)
        (map #(update (->> (str/split %1 #" ")
                           (zipmap [:direction :value]))
                      :value (fn [value] (Integer/parseInt value)))))))

(defn move
  [{:keys [direction value]} {:keys [horizontal depth] :as position}]
  (case direction
    "forward" (assoc position :horizontal (+ horizontal value))
    "down" (assoc position :depth (+ depth value))
    "up" (assoc position :depth (- depth value))))

(defn follow-course
  [data]
  (loop [position {:horizontal 0 :depth 0}
         [current & remaining] data]
    (if (nil? current)
      position
      (recur (move current position) remaining))))

(defn move-with-aim
  [{:keys [direction value]} {:keys [horizontal depth aim] :as position}]
  (case direction
    "forward" (assoc position
                     :horizontal (+ horizontal value)
                     :depth (+ depth (* aim value)))
    "down" (assoc position :aim (+ aim value))
    "up" (assoc position :aim (- aim value))))

(defn follow-course-with-aim
  [data]
  (loop [position {:horizontal 0 :depth 0 :aim 0}
         [current & remaining] data]
    (if (nil? current)
      position
      (recur (move-with-aim current position) remaining))))

(defn version-1
  []
  (->> (read-input)
       (follow-course)
       ((fn [{:keys [horizontal depth]}] (* horizontal depth)))))

(defn version-2
  []
  (->> (read-input)
       (follow-course-with-aim)
       ((fn [{:keys [horizontal depth]}] (* horizontal depth)))))
