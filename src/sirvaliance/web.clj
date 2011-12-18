(ns sirvaliance.web
  (:use [compojure.core]
        [ring.middleware.reload]
        [ring.middleware.stacktrace]
        [clostache.parser])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            ))

(defroutes app-routes 
  (GET "/" [] (render (slurp "templates/base.html") {:content (slurp "templates/main.html")
                                                     :head ""}))
  (route/files "/static" {:root "static"}))



(def app 
     (-> (var app-routes)
         (wrap-reload '(sirvaliance.web))
         (wrap-stacktrace)))

(defn start [port]
  (ring/run-jetty (var app) {:port (or port 8080) :join? false}))


(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (start port)))

