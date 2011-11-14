/*
 * Copyright (c) 2011. Alexandre Martins. All rights reserved.
 */

package pt.cnbc.wikimodels.dataVisitors

import xml._
import pt.cnbc.wikimodels.dataModel._
import pt.cnbc.wikimodels.util.SBMLHandler
import scala.collection.JavaConversions._
import scala.Predef._
import alexmsmartins.log.LoggerWrapper

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: 07-10-2011
 * Time: 19:09
 * To change this template use File | Settings | File Templates.
 */
object SBML2BeanConverter extends LoggerWrapper{

  def visit(e: Elem): Element = visitModel((e \ "model").head.asInstanceOf[Elem])

  def visitModel(m: Elem): SBMLModel = {

    var model = new SBMLModel(SBMLHandler.toStringOrNull((m \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(m),
      SBMLHandler.toStringOrNull((m \ "@id").text),
      SBMLHandler.toStringOrNull((m \ "@name").text))
    model.listOfFunctionDefinitions =
      (m \ "listOfFunctionDefinitions" \ "functionDefinition")
        .map(i => visitFunctionDefinition(i.asInstanceOf[scala.xml.Elem]))
    model.listOfCompartments =
      (m \ "listOfCompartments" \ "compartment")
        .map(i => visitCompartment(i.asInstanceOf[scala.xml.Elem]))
    model.listOfSpecies =
      (m \ "listOfSpecies" \ "species")
        .map(i => visitSpecies(i.asInstanceOf[scala.xml.Elem]))
    model.listOfParameters =
      (m \ "listOfParameters" \ "parameter")
        .map(i => visitParameter(i.asInstanceOf[scala.xml.Elem]))
    model.listOfConstraints =
      (m \ "listOfConstraints" \ "constraint")
        .map(i => visitConstraint(i.asInstanceOf[scala.xml.Elem]))
    model.listOfReactions =
      (m \ "listOfReactions" \ "reaction")
        .map(i => visitReaction(i.asInstanceOf[scala.xml.Elem]))
    model
  }

  def visitCompartment(c: Elem): Compartment = {
    new Compartment(SBMLHandler.toStringOrNull((c \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(c),
      SBMLHandler.toStringOrNull((c \ "@id").text),
      SBMLHandler.toStringOrNull((c \ "@name").text),
      try {
        (c \ "@compartmentType").text
      } catch {
        case _ => null
      },
      try {
        (c \ "@spatialDimensions").text.toInt
      } catch {
        case _ => 3
      },
      try {
        (c \ "@size").text.toDouble
      } catch {
        case _ => null
      },
      SBMLHandler.toStringOrNull((c \ "@units").text),
      SBMLHandler.toStringOrNull((c \ "@outside").text),
      try {
        (c \ "@constant").text.toBoolean
      } catch {
        case _ => true
      })
  }

  def visitConstraint(ct: Elem): Constraint = {
    new Constraint(
      SBMLHandler.toStringOrNull((ct \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(ct),
      SBMLHandler.toStringOrNull((ct \ "@id").text),
      SBMLHandler.toStringOrNull((ct \ "@name").text),
      (ct \ "math"),
      SBMLHandler.checkCurrentLabelForMessage(ct))
  }


  def visitFunctionDefinition(fd: Elem): FunctionDefinition = {
    new FunctionDefinition(
      SBMLHandler.toStringOrNull((fd \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(fd),
      SBMLHandler.toStringOrNull((fd \ "@id").text),
      SBMLHandler.toStringOrNull((fd \ "@name").text),
      (fd \ "math"))

  }

  def visitKineticLaw(kl: Elem): KineticLaw = {
    var kineticLaw = new KineticLaw(SBMLHandler.toStringOrNull((kl \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(kl),
      (kl \ "math"))
    kineticLaw.listOfParameters =
      (kl \ "listOfParameters" \ "parameter")
        .map(i => visitParameter(i.asInstanceOf[scala.xml.Elem]))
    kineticLaw
  }

  def visitModifierSpeciesReference(msr: Elem): ModifierSpeciesReference = {
    new ModifierSpeciesReference(
      SBMLHandler.toStringOrNull((msr \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(msr),
      SBMLHandler.toStringOrNull((msr \ "@id").text),
      SBMLHandler.toStringOrNull((msr \ "@name").text),
      SBMLHandler.toStringOrNull((msr \ "@species").text))


  }

  def visitParameter(p: Elem): Parameter = {
    var parameter =
      new Parameter(SBMLHandler.toStringOrNull((p \ "@metaid").text),
        SBMLHandler.checkCurrentLabelForNotes(p),
        SBMLHandler.toStringOrNull((p \ "@id").text),
        SBMLHandler.toStringOrNull((p \ "@name").text),
        null,
        SBMLHandler.toStringOrNull((p \ "@units").text),
        true
      )
    parameter.value = try {
      java.lang.Double.parseDouble((p \ "@value").text)
    } catch {
      case _ => null
    }
    parameter.constant = SBMLHandler
      .convertStringToBool((p \ "@constant").text, true)
    parameter

  }

  def visitReaction(r: Elem): Reaction = {
    var reaction = new Reaction(
      SBMLHandler.toStringOrNull((r \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(r),
      SBMLHandler.toStringOrNull((r \ "@id").text),
      SBMLHandler.toStringOrNull((r \ "@name").text),
      SBMLHandler.convertStringToBool((r \ "@reversible").text, true),
      SBMLHandler.convertStringToBool((r \ "@fast").text, false))
    reaction.listOfReactants =
      (r \ "listOfReactants" \ "speciesReference")
        .map(i => visitSpeciesReference(i.asInstanceOf[scala.xml.Elem]))
    reaction.listOfProducts =
      (r \ "listOfProducts" \ "speciesReference")
        .map(i => visitSpeciesReference(i.asInstanceOf[scala.xml.Elem]))
    reaction.listOfModifiers =
      (r \ "listOfModifiers" \ "modifierSpeciesReference")
        .map(i => visitModifierSpeciesReference(i.asInstanceOf[scala.xml.Elem]))
    if ((r \ "kineticLaw").length > 0)
      reaction.kineticLaw = visitKineticLaw((r \ "kineticLaw").head.asInstanceOf[scala.xml.Elem])
    reaction
  }

  def visitSpecies(s: Elem): Species = {
    new Species(
      SBMLHandler.toStringOrNull((s \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(s),
      SBMLHandler.toStringOrNull((s \ "@id").text),
      SBMLHandler.toStringOrNull((s \ "@name").text),
      SBMLHandler.toStringOrNull((s \ "@speciesType").text),
      SBMLHandler.toStringOrNull((s \ "@compartment").text),
      try {
        java.lang.Double.parseDouble((s \ "@initialAmount").text)
      } catch {
        case _ => null
      },
      try {
        java.lang.Double.parseDouble((s \ "@initialConcentration").text)
      } catch {
        case _ => null
      },
      SBMLHandler.toStringOrNull((s \ "@substanceUnits").text),
      try {
        (s \ "@hasOnlySubstanceUnits").text.toBoolean
      } catch {
        case _ => false
      },
      try {
        (s \ "@boundaryCondition").text.toBoolean
      } catch {
        case _ => false
      },
      try {
        (s \ "@constant").text.toBoolean
      } catch {
        case _ => false
      })

  }

  def visitSpeciesReference(sr: Elem): SpeciesReference = {
    var speciesReference = new SpeciesReference(SBMLHandler.toStringOrNull((sr \ "@metaid").text),
      SBMLHandler.checkCurrentLabelForNotes(sr),
      SBMLHandler.toStringOrNull((sr \ "@id").text),
      SBMLHandler.toStringOrNull((sr \ "@name").text),
      (sr \ "@species").text,
      try {
        (sr \ "@stoichiometry").text.toDouble
      } catch {
        case _ => 1
      },
      (sr \ "stoichiometryMath" \ "math"))
    //if there is a stoichiometryMath
    if ((sr \ "@speciesReference" \ "stoichiometryMath").size == 0) {
      if (speciesReference.stoichiometry == null)
        speciesReference.stoichiometry = 1
    } else {
      speciesReference.stoichiometry = null
    }
    speciesReference
  }
}