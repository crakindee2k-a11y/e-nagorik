package com.enagorik.web;

import com.enagorik.facade.ENagorikFacade;
import com.enagorik.model.Application;
import com.enagorik.model.Citizen;
import com.enagorik.model.StaffMember;
import com.enagorik.model.User;
import com.enagorik.util.JsonUtil;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

public class WebServer {
    private final HttpServer server;
    private final ENagorikFacade facade;

    public WebServer(int port, ENagorikFacade facade) throws IOException {
        this.facade = facade;
        this.server = HttpServer.create(new InetSocketAddress(port), 0);
        
        this.server.createContext("/api/services", new ServicesHandler());
        this.server.createContext("/api/applications", new ApplicationsHandler());
        
        this.server.setExecutor(null); // creates a default executor
    }

    public void start() {
        server.start();
        System.out.println("WebServer listening on port " + server.getAddress().getPort());
    }

    private void addCorsHeaders(HttpExchange exchange) {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type");
    }

    private void sendResponse(HttpExchange exchange, int statusCode, String response) throws IOException {
        addCorsHeaders(exchange);
        if (response == null) response = "";
        byte[] bytes = response.getBytes();
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(statusCode, bytes.length == 0 ? -1 : bytes.length);
        if (bytes.length > 0) {
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(bytes);
            }
        }
    }

    private void handleOptions(HttpExchange exchange) throws IOException {
        addCorsHeaders(exchange);
        exchange.sendResponseHeaders(204, -1);
    }

    private String readBody(HttpExchange exchange) {
        try (InputStream is = exchange.getRequestBody();
             Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A")) {
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            return "";
        }
    }

    class ServicesHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                handleOptions(exchange);
                return;
            }
            if ("GET".equals(exchange.getRequestMethod())) {
                sendResponse(exchange, 200, JsonUtil.toJson(facade.listServices()));
            } else {
                sendResponse(exchange, 405, "{\"error\":\"Method Not Allowed\"}");
            }
        }
    }

    class ApplicationsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("OPTIONS".equals(exchange.getRequestMethod())) {
                handleOptions(exchange);
                return;
            }

            String path = exchange.getRequestURI().getPath();
            String method = exchange.getRequestMethod();
            String[] segments = path.split("/");

            try {
                if ("POST".equals(method) && segments.length == 3 && "applications".equals(segments[2])) {
                    // POST /api/applications
                    Map<String, String> body = JsonUtil.parseJson(readBody(exchange));
                    int citizenId = Integer.parseInt(body.get("citizenId"));
                    int serviceId = Integer.parseInt(body.get("serviceId"));
                    
                    Citizen citizen = (Citizen) facade.getUserById(citizenId).orElseThrow(() -> new RuntimeException("Citizen not found"));
                    Application app = facade.applyForService(citizen, serviceId);
                    sendResponse(exchange, 200, JsonUtil.toJson(app));
                    return;
                }

                if (segments.length >= 4) {
                    String appId = segments[3];
                    Optional<Application> optApp = facade.trackApplication(appId);
                    if (!optApp.isPresent()) {
                        sendResponse(exchange, 404, "{\"error\":\"Application not found\"}");
                        return;
                    }
                    Application app = optApp.get();

                    if ("GET".equals(method) && segments.length == 4) {
                        // GET /api/applications/{id}
                        sendResponse(exchange, 200, JsonUtil.toJson(app));
                        return;
                    }

                    if ("POST".equals(method) && segments.length == 5) {
                        String action = segments[4];
                        Map<String, String> body = JsonUtil.parseJson(readBody(exchange));

                        if ("pay".equals(action)) {
                            String provider = body.get("provider");
                            String mobile = body.get("mobile");
                            facade.payForApplication(app, provider, mobile);
                            sendResponse(exchange, 200, JsonUtil.toJson(facade.trackApplication(appId).get()));
                            return;
                        } else if ("verify".equals(action)) {
                            int officerId = Integer.parseInt(body.get("officerId"));
                            StaffMember officer = (StaffMember) facade.getUserById(officerId).get();
                            facade.verifyApplication(officer, app);
                            sendResponse(exchange, 200, JsonUtil.toJson(facade.trackApplication(appId).get()));
                            return;
                        } else if ("reject".equals(action)) {
                            int officerId = Integer.parseInt(body.get("officerId"));
                            String reason = body.get("reason");
                            StaffMember officer = (StaffMember) facade.getUserById(officerId).get();
                            facade.rejectApplication(officer, app, reason);
                            sendResponse(exchange, 200, JsonUtil.toJson(facade.trackApplication(appId).get()));
                            return;
                        } else if ("request-otp".equals(action)) {
                            String otp = facade.requestMayorOtp(app);
                            sendResponse(exchange, 200, "{\"otp\":\"" + otp + "\"}");
                            return;
                        } else if ("approve".equals(action)) {
                            int mayorId = Integer.parseInt(body.get("mayorId"));
                            String otp = body.get("otp");
                            StaffMember mayor = (StaffMember) facade.getUserById(mayorId).get();
                            facade.approveApplication(mayor, app, otp);
                            sendResponse(exchange, 200, JsonUtil.toJson(facade.trackApplication(appId).get()));
                            return;
                        }
                    }
                }
                
                sendResponse(exchange, 404, "{\"error\":\"Not Found\"}");
            } catch (Exception e) {
                e.printStackTrace();
                sendResponse(exchange, 500, "{\"error\":\"" + e.getMessage() + "\"}");
            }
        }
    }
}
