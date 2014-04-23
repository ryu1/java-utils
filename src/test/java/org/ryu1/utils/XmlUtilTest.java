package org.ryu1.utils;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ryu
 * Date: 2014/03/22
 * Time: 16:46
 * To change this template use File | Settings | File Templates.
 */
public class XmlUtilTest {
    @org.junit.Test
    public void testFromXML() throws Exception {
        String xml = "<parent>\n" +
                "  <name>John</name>\n" +
                "  <age>40</age>\n" +
                "  <children>\n" +
                "    <child>\n" +
                "      <name>Micheal</name>\n" +
                "      <age>12</age>\n" +
                "    </child>\n" +
                "    <child>\n" +
                "      <name>Jessica</name>\n" +
                "      <age>7</age>\n" +
                "    </child>\n" +
                "  </children>\n" +
                "</parent>";

        Parent john = XmlUtil.fromXML(Parent.class, xml);
        System.out.println(john);
    }

    @org.junit.Test
    public void testToXML() throws Exception {
        Parent john = new Parent();
        john.setAge(40);
        john.setName("John");

        Child micheal = new Child();
        micheal.setAge(12);
        micheal.setName("Micheal");
        Child jessica = new Child();
        jessica.setAge(7);
        jessica.setName("Jessica");

        List<Child> children = new ArrayList<Child>();
        children.add(micheal);
        children.add(jessica);
        john.setChildren(children);

        String xml = XmlUtil.toXML(john);
        System.out.println(xml);
    }

    @XStreamAlias("parent")
    public static class Parent {
        @XStreamAlias("name")
        private String name;
        @XStreamAlias("age")
        private int age;
        @XStreamAlias("children")
        private List<Child> children;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public List<Child> getChildren() {
            return children;
        }

        public void setChildren(List<Child> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            return "Parent{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    ", children=" + children +
                    '}';
        }
    }

    @XStreamAlias("child")
    public static class Child {
        @XStreamAlias("name")
        private String name;
        @XStreamAlias("age")
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Child{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }
}
