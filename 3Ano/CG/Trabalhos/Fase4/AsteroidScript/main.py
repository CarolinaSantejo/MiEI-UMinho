from lxml import etree
import random

mytree = etree.parse('novoSS.xml')
myroot = mytree.getroot()


def draw(radiusMin, radiusMax,numA):
    for i in range(numA):

        angle = random.uniform(0, 360)
        radius = random.uniform(radiusMin, radiusMax)
        altura = random.uniform(-0.1, 0.1)
        r = random.uniform(0.48, 0.53)
        g = random.uniform(0.48, 0.53)
        b = random.uniform(0.48, 0.53)

        scale = random.uniform(0.15, 0.25)
        time = random.uniform(20,30)

        global myroot
        myroot[8].tail = '\n\t'
        doc = etree.SubElement(myroot, "group")
        doc.tail = '\n\t'
        doc.text ='\n\t\t'


        field1 = etree.SubElement(doc, "rotate")
        field1.set("angle", str(angle))
        field1.set("axisX", "0")
        field1.set("axisY", "1")
        field1.set("axisZ", "0")
        field1.tail = '\n\t\t'


        field3 = etree.SubElement(doc, "rotate")
        field3.set("time", str(time))
        field3.set("axisX", "0")
        field3.set("axisY", "-1")
        field3.set("axisZ", "0")
        field3.tail = '\n\t\t'


        field4 = etree.SubElement(doc, "group")
        field4.tail = '\n\t'
        field4.text = '\n\t\t'

        field5 = etree.SubElement(field4, "translate")
        field5.set("X", str(radius))
        field5.set("Y", str(altura))
        field5.set("Z", "0")
        field5.tail = '\n\t\t\t'

        field6 = etree.SubElement(field4, "scale")
        field6.set("X", str(scale))
        field6.set("Y", str(scale))
        field6.set("Z", str(scale))
        field6.tail = '\n\t\t\t'

        field7 = etree.SubElement(field4, "models")
        field7.tail = '\n\t\t'
        field7.text ='\n\t\t\t\t'

        field8 = etree.SubElement(field7, "model")
        field8.set("file","asteroid.3d")
        field8.set("diffR", str(r))
        field8.set("diffG", str(g))
        field8.set("diffB", str(b))
        field8.tail ='\n\t\t\t'
        i+=1
    doc.tail='\n'


draw(5.5,6.6,500)
draw(15,17.7,450)

mytree.write('SistemaSolar.xml')