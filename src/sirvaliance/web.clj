(ns sirvaliance.web
  (:use [compojure.core]
        [ring.middleware.reload]
        [ring.middleware.stacktrace]
        [stencil.core])
  (:require [compojure.route :as route]
            [compojure.handler :as handler]
            [ring.adapter.jetty :as ring]
            ))


(defn render-temp [base main]
  (render-string (slurp base) {:content (slurp main)
                               :head ""}))

(defroutes app-routes 
  (GET "/" [] (render-temp "templates/base.html" "templates/main.html"))
  (GET "/mozilla/" [] (render-temp "templates/mozilla_base.html" "templates/mozilla.html"))
  (GET "/apple/" [] (render-temp "templates/base.html" "templates/main.html"))
  (GET "/bump/" [] (render-temp "templates/base.html" "templates/main.html"))
  (GET "/bittorrent/" [] (render-temp "templates/base.html" "templates/main.html"))
  (GET "/mixpanel/" [] (render-temp "templates/base.html" "templates/main.html"))
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

