package com.example.application.views;

import com.example.application.data.User;
import com.example.application.security.AuthenticatedUser;
import com.example.application.views.client.ClientView;
import com.example.application.views.contracts.ContractsView;
import com.example.application.views.contracts.CreateContractView;
import com.example.application.views.debitors.DebitorsView;
import com.example.application.views.debtors.CreateDebtorView;
import com.example.application.views.debtors.DebtorsView;
import com.example.application.views.policyholder.CreatePolicyholderView;
import com.example.application.views.policyholder.PolicyholderView;
import com.example.application.views.requests.CreateRequestView;
import com.example.application.views.requests.RequestsView;
import com.example.application.views.users.CreateUserView;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.contextmenu.MenuItem;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Footer;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Header;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.orderedlayout.Scroller;
import com.vaadin.flow.component.sidenav.SideNav;
import com.vaadin.flow.component.sidenav.SideNavItem;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.server.StreamResource;
import com.vaadin.flow.server.auth.AccessAnnotationChecker;
import com.vaadin.flow.theme.lumo.LumoUtility;
import java.io.ByteArrayInputStream;
import java.util.Optional;
import org.vaadin.lineawesome.LineAwesomeIcon;

/**
 * The main view is a top-level placeholder for other views.
 */
public class MainLayout extends AppLayout {

    private H1 viewTitle;

    private AuthenticatedUser authenticatedUser;
    private AccessAnnotationChecker accessChecker;

    public MainLayout(AuthenticatedUser authenticatedUser, AccessAnnotationChecker accessChecker) {
        this.authenticatedUser = authenticatedUser;
        this.accessChecker = accessChecker;

        setPrimarySection(Section.DRAWER);
        addDrawerContent();
        addHeaderContent();
    }

    private void addHeaderContent() {
        DrawerToggle toggle = new DrawerToggle();
        toggle.setAriaLabel("Menu toggle");

        viewTitle = new H1();
        viewTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.Margin.NONE);

        addToNavbar(true, toggle, viewTitle);
    }

    private void addDrawerContent() {
        Span appName = new Span("Credendo");
        appName.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.FontSize.LARGE);
        Header header = new Header(appName);

        Scroller scroller = new Scroller(createNavigation());

        addToDrawer(header, scroller, createFooter());
        getStyle().set("font-size", "smaller");
    }

    private SideNav createNavigation() {
        SideNav nav = new SideNav();

        if (accessChecker.hasAccess(CreateContractView.class)) {
            nav.addItem(new SideNavItem("Создать контракт", CreateContractView.class,
                    LineAwesomeIcon.ADDRESS_BOOK_SOLID.create()));

        }
        if (accessChecker.hasAccess(ContractsView.class)) {
            nav.addItem(new SideNavItem("Список контрактов", ContractsView.class,
                    LineAwesomeIcon.ADDRESS_BOOK_SOLID.create()));

        }
        if (accessChecker.hasAccess(CreateDebtorView.class)) {
            nav.addItem(new SideNavItem("Создать дебитора", CreateDebtorView.class, LineAwesomeIcon.USER.create()));

        }
        if (accessChecker.hasAccess(DebtorsView.class)) {
            nav.addItem(
                    new SideNavItem("Список дебиторов", DebtorsView.class,
                            LineAwesomeIcon.ADDRESS_BOOK_SOLID.create()));
        }
        if (accessChecker.hasAccess(CreatePolicyholderView.class)) {
            nav.addItem(new SideNavItem("Создать страхователя", CreatePolicyholderView.class,
                    LineAwesomeIcon.USER.create()));

        }
        if (accessChecker.hasAccess(PolicyholderView.class)) {
            nav.addItem(
                    new SideNavItem("Список страхователей", PolicyholderView.class,
                            LineAwesomeIcon.ADDRESS_BOOK_SOLID.create()));
        }
        if (accessChecker.hasAccess(CreateRequestView.class)) {
            nav.addItem(
                    new SideNavItem("Создать кредитный лимит", CreateRequestView.class, LineAwesomeIcon.USER.create()));

        }
        if (accessChecker.hasAccess(RequestsView.class)) {
            nav.addItem(
                    new SideNavItem("Список кредитных лимитов", RequestsView.class,
                            LineAwesomeIcon.ADDRESS_BOOK_SOLID.create()));
        }
        if (accessChecker.hasAccess(CreateUserView.class)) {
            nav.addItem(
                    new SideNavItem("Добавить пользователя", CreateUserView.class, LineAwesomeIcon.USER.create()));

        }

        // if (accessChecker.hasAccess(ClientView.class)) {
        // nav.addItem(new SideNavItem("Клиенты", ClientView.class,
        // LineAwesomeIcon.ADDRESS_BOOK_SOLID.create()));

        // }
        // if (accessChecker.hasAccess(DebitorsView.class)) {
        // nav.addItem(new SideNavItem("Дебиторы", DebitorsView.class,
        // LineAwesomeIcon.TH_SOLID.create()));

        // }
        // if (accessChecker.hasAccess(NewApplicationView.class)) {
        // nav.addItem(new SideNavItem("Новая заявка", NewApplicationView.class,
        // LineAwesomeIcon.USER.create()));

        // }

        return nav;
    }

    private Footer createFooter() {
        Footer layout = new Footer();

        Optional<User> maybeUser = authenticatedUser.get();
        if (maybeUser.isPresent()) {
            User user = maybeUser.get();

            // Avatar avatar = new Avatar(user.getName());
            // StreamResource resource = new StreamResource("profile-pic",
            // () -> new ByteArrayInputStream(user.getProfilePicture()));
            // avatar.setImageResource(resource);
            // avatar.setThemeName("xsmall");
            // avatar.getElement().setAttribute("tabindex", "-1");

            MenuBar userMenu = new MenuBar();
            userMenu.setThemeName("tertiary-inline contrast");

            MenuItem userName = userMenu.addItem("");
            Div div = new Div();
            // div.add(avatar);
            div.add(user.getName());
            div.add(new Icon("lumo", "dropdown"));
            div.getElement().getStyle().set("display", "flex");
            div.getElement().getStyle().set("align-items", "center");
            div.getElement().getStyle().set("gap", "var(--lumo-space-s)");
            userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> {
                authenticatedUser.logout();
            });

            layout.add(userMenu);
        } else {
            Anchor loginLink = new Anchor("login", "Sign in");
            layout.add(loginLink);
        }

        return layout;
    }

    @Override
    protected void afterNavigation() {
        super.afterNavigation();
        viewTitle.setText(getCurrentPageTitle());
    }

    private String getCurrentPageTitle() {
        PageTitle title = getContent().getClass().getAnnotation(PageTitle.class);
        return title == null ? "" : title.value();
    }
}
