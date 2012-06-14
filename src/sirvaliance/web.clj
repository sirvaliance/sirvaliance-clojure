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
  (render-string (slurp (str "templates/" base)) {:content (slurp (str "templates/" main))
                                                  :head ""}))


(defroutes app-routes 
  (GET "/" [] (render-temp "base.html" "main.html"))
  (GET "/mozilla/" [] (render-temp "mozilla_base.html" "mozilla.html"))
  (GET "/robots.txt" [] "User-agent: * \r\n Disallow:")
  (route/files "/static" {:root "static"})
  (route/not-found (render-temp "base.html" "404.html")))


(def app 
     (-> (var app-routes)
         (wrap-reload '(sirvaliance.web))
         (wrap-stacktrace)))

(defn start [port]
  (ring/run-jetty (var app) {:port (or port 8080) :join? false}))


(defn -main []
  (let [port (Integer/parseInt (get (System/getenv) "PORT" "8080"))]
    (start port)))

