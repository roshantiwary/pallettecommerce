"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var core_1 = require("@angular/core");
var platform_browser_1 = require("@angular/platform-browser");
var common_1 = require("@angular/common");
var router_1 = require("@angular/router");
var forms_1 = require("@angular/forms");
var LoginComponent_1 = require("./LoginComponent");
var RegistrationComponent_1 = require("./RegistrationComponent");
var AccountRoutingComponent_1 = require("./AccountRoutingComponent");
var AccountSpaComponent_1 = require("./AccountSpaComponent");
var NavComponent_1 = require("./NavComponent");
var HomeComponent_1 = require("./HomeComponent");
var http_1 = require("@angular/http");
var AccountModule = (function () {
    function AccountModule() {
    }
    return AccountModule;
}());
AccountModule = __decorate([
    core_1.NgModule({
        imports: [platform_browser_1.BrowserModule, forms_1.FormsModule, router_1.RouterModule.forRoot(AccountRoutingComponent_1.ApplicationRoutes), http_1.HttpModule],
        declarations: [LoginComponent_1.LoginComponent, RegistrationComponent_1.RegistrationComponent, AccountSpaComponent_1.AccountSpaComponent, NavComponent_1.NavComponent, HomeComponent_1.HomeComponent],
        providers: [{ provide: common_1.LocationStrategy, useClass: common_1.HashLocationStrategy }],
        bootstrap: [AccountSpaComponent_1.AccountSpaComponent, NavComponent_1.NavComponent]
    })
], AccountModule);
exports.AccountModule = AccountModule;
//# sourceMappingURL=accountModule.js.map