package TestHaulmont;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;

import TestHaulmont.jpa.Factory;
import TestHaulmont.jpa.entity.Group;
import TestHaulmont.jpa.entity.Student;
import TestHaulmont.util.CustomIntegerRangeValidator;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.renderers.*;

import java.util.*;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
@Widgetset("TestHaulmont.MyAppWidgetset")
public class MyUI extends UI {
    Grid grid;
    Grid grid2;
    GeneratedPropertyContainer containerStudents;
    @Override
    protected void init(VaadinRequest vaadinRequest) {
/////////////////////////ТАБЛИЦА ГРУПП//////////////////////////////////////////////////////////////
        grid=new Grid();
        updateList();
        grid.setWidth("800px");
        grid.setHeight("200px");

        grid.setColumns("number","facultet","update","delete");
        grid.getColumn("delete")
                .setRenderer(new ButtonRenderer(e ->
                {
                    Group group= (Group) e.getItemId();
                    try {
                        Factory.getInstance().getGroupDAO().delete(group);
                    }catch (Exception s){
                        new Notification("Удаление группы невозможно.Группа содержит студентов.").show(Page.getCurrent());

                    }
                    updateList();
                }));
        grid.getColumn("update")
                .setRenderer(new ButtonRenderer(e ->
                {
                    setSubWindowGroup((Group) e.getItemId());
                }));

/////////////////////////ТАБЛИЦА СТУДЕНТОВ///////////////////////////////////////////////////////////////
        grid2 = new Grid();
        updateListStudents();
        grid2.setWidth("800px");
        grid2.setHeight("200px");
        grid2.setColumns("name","surname","groupNumber","age","update","delete");
        grid2.getColumn("delete")
                .setRenderer(new ButtonRenderer(e ->
                {
                    Student s= (Student) e.getItemId();
                    Factory.getInstance().getStudentDAO().delete(s);
                    updateListStudents();
                }));
        grid2.getColumn("update")
                .setRenderer(new ButtonRenderer(e ->
                {
                    setSubWindowStudent((Student) e.getItemId());

                }));

        grid2.getColumn("age").setRenderer(new DateRenderer("%1$tB %1$te, %1$tY",
                Locale.ENGLISH));


/////////////////////ДОБАВЛЕНИЕ ГРУППЫ////////////////////////////////////////////////////////////////
        Panel panel=new Panel("Добавление группы:");
        panel.addStyleName("mypanelexample");
        panel.setSizeUndefined();
        Button bg1=new Button("Сохранить");
        Button bg2=new Button("Отменить");
        TextField textG1=new TextField("Номер группы:");
        textG1.addValidator(new CustomIntegerRangeValidator("Only Integer between 1 and 10000!",1,10000));
        TextField textG2=new TextField("Факультет:");
        textG2.addValidator(new StringLengthValidator(
                "Must be between 2 and 20 characters in length", 2, 20, false));

        FormLayout content = new FormLayout();
        content.addStyleName("mypanelcontent");
        content.addComponent(textG1);
        content.addComponent(textG2);
        content.addComponent(new HorizontalLayout(bg1,bg2));
        content.setSizeUndefined();
        content.setMargin(true);
        panel.setContent(content);
        panel.setVisible(false);

        bg2.addClickListener(e->{panel.setVisible(false);textG1.clear();textG2.clear();});
        bg1.addClickListener(e->{
            if (textG1.isValid()&&textG2.isValid()){
                List<Group> list=Factory.getInstance().getGroupDAO().getAll();
                Boolean b=false;
                for (Group g:list){
                    if (String.valueOf(g.getNumber()).equals(textG1.getValue())){
                        new Notification("Введеная группа уже существует").show(Page.getCurrent());
                        b=true;
                        break;
                    }
                }
                if(b==false) {
                    Group group = new Group();
                    group.setId(new Random().nextLong());
                    group.setNumber(Integer.valueOf(textG1.getValue()));
                    group.setFacultet(textG2.getValue());
                    Factory.getInstance().getGroupDAO().add(group);
                    updateList();
                    panel.setVisible(false);
                    textG1.clear();
                    textG2.clear();
                }
            }else new Notification("Введены недопустимые значения").show(Page.getCurrent());
        });
/////////////////////////ДОБАВЛЕНИЕ СТУДЕНТа//////////////////////////////////////////////////////////////
        Panel panel2=new Panel("Добавление студента:");
        panel2.addStyleName("mypanelexample");
        panel2.setSizeUndefined();
        Button bs1=new Button("Сохранить");
        Button bs2=new Button("Отменить");
        TextField textS1=new TextField("Имя:");
        textS1.addValidator(new StringLengthValidator(
                "Must be between 2 and 20 characters in length", 2, 20, false));
        TextField textS2=new TextField("Фамилия:");
        textS2.addValidator(new StringLengthValidator(
                "Must be between 2 and 20 characters in length", 2, 20, false));
        TextField textS4=new TextField("Номер группы:");
        textS4.addValidator(new CustomIntegerRangeValidator("Only Integer between 1 and 10000!",1,10000));
        DateField textS3=new DateField("Дата рождения:");

        FormLayout content2 = new FormLayout();
        content2.addStyleName("mypanelcontent");
        content2.addComponent(textS1);
        content2.addComponent(textS2);
        content2.addComponent(textS4);
        content2.addComponent(textS3);
        content2.addComponent(new HorizontalLayout(bs1,bs2));
        content2.setSizeUndefined();
        content2.setMargin(true);
        panel2.setContent(content2);
        panel2.setVisible(false);

        bs2.addClickListener(e->{panel2.setVisible(false);textS1.clear();textS2.clear();textS3.clear();textS4.clear();});
        bs1.addClickListener(e->{
            if (textS1.isValid()&&textS2.isValid()&&textS4.isValid()) {
                    Student student = new Student();
                    student.setId(new Random().nextLong());
                    student.setName(textS1.getValue());
                    student.setSurname(textS2.getValue());
                    try {
                        student.setGroup(Factory.getInstance().getGroupDAO().getByNumber(Integer.valueOf(textS4.getValue())));
                        student.setAge(textS3.getValue());
                        Factory.getInstance().getStudentDAO().add(student);
                        updateListStudents();
                        panel2.setVisible(false);
                        textS1.clear();
                        textS2.clear();
                        textS3.clear();
                        textS4.clear();
                    }catch (Exception s){
                        new Notification("Данной группы не существует").show(Page.getCurrent());
                    }
            }else new Notification("Введены недопустимые значения").show(Page.getCurrent());
        });
////////////////////////////////////////////////////////////////////////////////////////
        Label l1=new Label("Таблица Группы");
        Label l2=new Label("Таблица Студенты");
        Button b1=new Button("Создать новую группу");
        Button b2=new Button("Создать нового студенты");
        b1.addClickListener(e->panel.setVisible(true));
        b2.addClickListener(e->panel2.setVisible(true));
        TextField filterSurname=new TextField("Фамилия");
        TextField filterNumber=new TextField("Номер группы");
        Button bFilter=new Button("Применить фильтр");
        bFilter.addClickListener(clickEvent -> {
            if(!filterSurname.getValue().equals("")||!filterNumber.getValue().equals("")){
                if(!filterSurname.getValue().equals("")){
                    Container.Filter filter=new SimpleStringFilter("surname",filterSurname.getValue(),false,false);
                    containerStudents.addContainerFilter(filter);
                }
                if(!filterNumber.getValue().equals("")) {
                    Container.Filter filter2 = new SimpleStringFilter("groupNumber", filterNumber.getValue(), false, false);
                    containerStudents.addContainerFilter(filter2);
                }
            }
            else containerStudents.removeAllContainerFilters();
            grid2.setContainerDataSource(containerStudents);
        });

        final VerticalLayout layout = new VerticalLayout();
        layout.addComponent(l1);
        layout.addComponent(b1);
            HorizontalLayout hl1=new HorizontalLayout(grid,panel);
            hl1.setSpacing(true);
        layout.addComponent(hl1);
        layout.addComponent(l2);
        layout.addComponent(b2);
        layout.addComponent(new HorizontalLayout(filterSurname,filterNumber));
        layout.addComponent(bFilter);
            HorizontalLayout hl2=new HorizontalLayout(grid2,panel2);
            hl2.setSpacing(true);
        layout.addComponent(hl2);
        layout.setMargin(true);
        layout.setSpacing(true);

        setContent(layout);
    }
    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }

    private GeneratedPropertyContainer getContainer (BeanItemContainer container){
        GeneratedPropertyContainer gpc=
                new GeneratedPropertyContainer(container);
        gpc.addGeneratedProperty("delete",
                new PropertyValueGenerator<String>() {

                    @Override
                    public String getValue(Item item, Object itemId,
                                           Object propertyId) {
                        return "Delete";
                    }

                    @Override
                    public Class<String> getType() {
                        return String.class;
                    }
                });
        gpc.addGeneratedProperty("update",
                new PropertyValueGenerator<String>() {

                    @Override
                    public String getValue(Item item, Object itemId,
                                           Object propertyId) {
                        return "Update";
                    }

                    @Override
                    public Class<String> getType() {
                        return String.class;
                    }
                });

        return gpc;
    }
    private void updateList() {
        List<Group> list= Factory.getInstance().getGroupDAO().getAll();
        grid.setContainerDataSource(getContainer(new BeanItemContainer<>(Group.class, list)));

    }
    private void updateListStudents() {
        List<Student> listStudent=Factory.getInstance().getStudentDAO().getAll();
        BeanItemContainer<Student> container=new BeanItemContainer<Student>(Student.class, listStudent);
        containerStudents=getContainer(container);
        grid2.setContainerDataSource(containerStudents);
    }

    private void setSubWindowGroup(Group item){
        Window subWindow = new Window();
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        subWindow.setContent(subContent);

        Button bg1=new Button("Сохранить");
        TextField textG1=new TextField("Номер группы:");
        textG1.addValidator(new CustomIntegerRangeValidator("Only Integer between 1 and 10000!",1,10000));
        textG1.setValue(String.valueOf(item.getNumber()));
        TextField textG2=new TextField("Факультет:");
        textG2.setValue(item.getFacultet());
        textG2.addValidator(new StringLengthValidator(
                "Must be between 2 and 20 characters in length", 2, 20, false));
        subContent.addComponent(textG1);
        subContent.addComponent(textG2);
        subContent.addComponent(bg1);
        bg1.addClickListener(clickEvent -> {
            if (textG1.isValid()&&textG2.isValid()) {
                Group group = new Group();
                group.setId(item.getId());
                group.setNumber(Integer.valueOf(textG1.getValue()));
                group.setFacultet(textG2.getValue());
                Factory.getInstance().getGroupDAO().update(group);
                subWindow.close();
                updateList();
                updateListStudents();
            }else new Notification("Введены недопустимые значения").show(Page.getCurrent());

        });
        subWindow.center();
        subWindow.setModal(true);
        subWindow.setResizable(false);
        subWindow.setDraggable(false);
        subWindow.setWidth("500px");
        subWindow.setHeight("300px");
        subWindow.setHeightUndefined();
        subWindow.setWidthUndefined();
        addWindow(subWindow);
    }
    private void setSubWindowStudent(Student item){
        Window subWindow = new Window();
        VerticalLayout subContent = new VerticalLayout();
        subContent.setMargin(true);
        subContent.setSpacing(true);
        subWindow.setContent(subContent);

        Button bs1=new Button("Сохранить");
        TextField textS1=new TextField("Имя:");
        textS1.setValue(item.getName());
        textS1.addValidator(new StringLengthValidator(
                "Must be between 2 and 20 characters in length", 2, 20, false));
        TextField textS2=new TextField("Фамилия:");
        textS2.setValue(item.getSurname());
        textS2.addValidator(new StringLengthValidator(
                "Must be between 2 and 20 characters in length", 2, 20, false));
        TextField textS4=new TextField("Номер группы:");
        textS4.setValue(String.valueOf(item.getGroup().getNumber()));
        textS4.addValidator(new CustomIntegerRangeValidator("Only Integer between 1 and 10000!",1,10000));
        textS4.setEnabled(false);
        DateField textS3=new DateField("Дата рождения:");
        textS3.setValue(item.getAge());
        subContent.addComponent(textS1);
        subContent.addComponent(textS2);
        subContent.addComponent(textS3);
        subContent.addComponent(textS4);
        subContent.addComponent(bs1);
        bs1.addClickListener(clickEvent -> {
            if (textS1.isValid()&&textS2.isValid()&&textS4.isValid()) {
                Student student = new Student();
                student.setId(item.getId());
                student.setName(textS1.getValue());
                student.setSurname(textS2.getValue());
                student.setAge(textS3.getValue());
                student.setGroup(Factory.getInstance().getGroupDAO().getByNumber(Integer.valueOf(textS4.getValue())));
                Factory.getInstance().getStudentDAO().update(student);
                subWindow.close();
                updateListStudents();
            }else new Notification("Введены недопустимые значения").show(Page.getCurrent());
        });
        subWindow.center();
        subWindow.setModal(true);
        subWindow.setResizable(false);
        subWindow.setDraggable(false);
        subWindow.setWidth("500px");
        subWindow.setHeight("700px");
        subWindow.setHeightUndefined();
        subWindow.setWidthUndefined();
        addWindow(subWindow);
    }

    
}
