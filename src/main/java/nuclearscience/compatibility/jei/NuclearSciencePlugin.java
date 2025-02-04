package nuclearscience.compatibility.jei;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.google.common.collect.ImmutableSet;

import electrodynamics.client.screen.tile.ScreenO2OProcessor;
import electrodynamics.common.recipe.categories.fluiditem2fluid.FluidItem2FluidRecipe;
import electrodynamics.common.recipe.categories.fluiditem2item.FluidItem2ItemRecipe;
import electrodynamics.common.recipe.categories.item2item.Item2ItemRecipe;
import electrodynamics.compatibility.jei.ElectrodynamicsJEIPlugin;
import electrodynamics.compatibility.jei.recipecategories.psuedo.PsuedoItem2ItemRecipe;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import nuclearscience.client.screen.ScreenChemicalExtractor;
import nuclearscience.client.screen.ScreenGasCentrifuge;
import nuclearscience.client.screen.ScreenMSRFuelPreProcessor;
import nuclearscience.client.screen.ScreenNuclearBoiler;
import nuclearscience.client.screen.ScreenParticleInjector;
import nuclearscience.client.screen.ScreenRadioactiveProcessor;
import nuclearscience.client.screen.ScreenReactorCore;
import nuclearscience.common.recipe.NuclearScienceRecipeInit;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2fluid.specificmachines.NuclearBoilerRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines.ChemicalExtractorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines.MSRProcessorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.fluiditem2item.specificmachines.RadioactiveProcessorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.item2item.FissionReactorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.item2item.FuelReprocessorRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines.GasCentrifugeRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines.ParticleAcceleratorAntiMatterRecipeCategory;
import nuclearscience.compatibility.jei.recipecategories.psuedo.specificmachines.ParticleAcceleratorDarkMatterRecipeCategory;
import nuclearscience.compatibility.jei.utils.psuedorecipes.NuclearSciencePsuedoRecipes;
import nuclearscience.compatibility.jei.utils.psuedorecipes.PsuedoGasCentrifugeRecipe;

@JeiPlugin
public class NuclearSciencePlugin implements IModPlugin {

	private static final String INFO_BLOCK = "jei.info.block.";

	@Override
	public ResourceLocation getPluginUid() {
		return new ResourceLocation(nuclearscience.References.ID, "jei");
	}

	@Override
	public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {

		registration.addRecipeCatalyst(GasCentrifugeRecipeCategory.INPUT_MACHINE, GasCentrifugeRecipeCategory.UID);
		registration.addRecipeCatalyst(NuclearBoilerRecipeCategory.INPUT_MACHINE, NuclearBoilerRecipeCategory.UID);
		registration.addRecipeCatalyst(ChemicalExtractorRecipeCategory.INPUT_MACHINE, ChemicalExtractorRecipeCategory.UID);
		registration.addRecipeCatalyst(FissionReactorRecipeCategory.INPUT_MACHINE, FissionReactorRecipeCategory.UID);
		registration.addRecipeCatalyst(ParticleAcceleratorAntiMatterRecipeCategory.INPUT_MACHINE, ParticleAcceleratorAntiMatterRecipeCategory.UID);
		registration.addRecipeCatalyst(ParticleAcceleratorDarkMatterRecipeCategory.INPUT_MACHINE, ParticleAcceleratorDarkMatterRecipeCategory.UID);
		registration.addRecipeCatalyst(FuelReprocessorRecipeCategory.INPUT_MACHINE, FuelReprocessorRecipeCategory.UID);
		registration.addRecipeCatalyst(RadioactiveProcessorRecipeCategory.INPUT_MACHINE, RadioactiveProcessorRecipeCategory.UID);
		registration.addRecipeCatalyst(MSRProcessorRecipeCategory.INPUT_MACHINE, MSRProcessorRecipeCategory.UID);
	}

	@Override
	public void registerRecipes(IRecipeRegistration registration) {
		NuclearSciencePsuedoRecipes.addNuclearScienceRecipes();
		Minecraft mc = Minecraft.getInstance();
		ClientLevel world = Objects.requireNonNull(mc.level);
		RecipeManager recipeManager = world.getRecipeManager();

		// Gas Centrifuge
		Set<PsuedoGasCentrifugeRecipe> gasCentrifugeRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.GAS_CENTRIFUGE_RECIPES);
		registration.addRecipes(gasCentrifugeRecipes, GasCentrifugeRecipeCategory.UID);

		// Nuclear Boiler
		Set<FluidItem2FluidRecipe> nuclearBoilerRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.NUCLEAR_BOILER_TYPE));
		registration.addRecipes(nuclearBoilerRecipes, NuclearBoilerRecipeCategory.UID);

		// Chemical Extractor
		Set<FluidItem2ItemRecipe> chemicalExtractorRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.CHEMICAL_EXTRACTOR_TYPE));
		registration.addRecipes(chemicalExtractorRecipes, ChemicalExtractorRecipeCategory.UID);

		// Fission Reactor
		Set<Item2ItemRecipe> fissionReactorRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.FISSION_REACTOR_TYPE));
		registration.addRecipes(fissionReactorRecipes, FissionReactorRecipeCategory.UID);

		// Anti-Matter
		Set<PsuedoItem2ItemRecipe> antiMatterRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.ANTI_MATTER_RECIPES);
		registration.addRecipes(antiMatterRecipes, ParticleAcceleratorAntiMatterRecipeCategory.UID);

		// Dark Matter
		Set<PsuedoItem2ItemRecipe> darkMatterRecipes = new HashSet<>(NuclearSciencePsuedoRecipes.DARK_MATTER_RECIPES);
		registration.addRecipes(darkMatterRecipes, ParticleAcceleratorDarkMatterRecipeCategory.UID);

		// Fuel Reprocessor
		Set<Item2ItemRecipe> fuelReprocessorRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.FUEL_REPROCESSOR_TYPE));
		registration.addRecipes(fuelReprocessorRecipes, FuelReprocessorRecipeCategory.UID);

		// Radioactive Processor
		Set<FluidItem2ItemRecipe> radioactiveProcessorRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.RADIOACTIVE_PROCESSOR_TYPE));
		registration.addRecipes(radioactiveProcessorRecipes, RadioactiveProcessorRecipeCategory.UID);

		// MSR Processor
		Set<FluidItem2ItemRecipe> msrProcessorRecipes = ImmutableSet.copyOf(recipeManager.getAllRecipesFor(NuclearScienceRecipeInit.MSR_FUEL_PREPROCESSOR_TYPE));
		registration.addRecipes(msrProcessorRecipes, MSRProcessorRecipeCategory.UID);

		nuclearScienceInfoTabs(registration);
	}

	@Override
	public void registerCategories(IRecipeCategoryRegistration registration) {

		IGuiHelper helper = registration.getJeiHelpers().getGuiHelper();

		registration.addRecipeCategories(new GasCentrifugeRecipeCategory(helper));
		registration.addRecipeCategories(new NuclearBoilerRecipeCategory(helper));
		registration.addRecipeCategories(new ChemicalExtractorRecipeCategory(helper));
		registration.addRecipeCategories(new FissionReactorRecipeCategory(helper));
		registration.addRecipeCategories(new ParticleAcceleratorAntiMatterRecipeCategory(helper));
		registration.addRecipeCategories(new ParticleAcceleratorDarkMatterRecipeCategory(helper));
		registration.addRecipeCategories(new FuelReprocessorRecipeCategory(helper));
		registration.addRecipeCategories(new RadioactiveProcessorRecipeCategory(helper));
		registration.addRecipeCategories(new MSRProcessorRecipeCategory(helper));

	}

	@Override
	public void registerGuiHandlers(IGuiHandlerRegistration registry) {

		registry.addRecipeClickArea(ScreenO2OProcessor.class, 48, 35, 22, 15, ElectrodynamicsJEIPlugin.O2O_CLICK_AREAS.toArray(new ResourceLocation[ElectrodynamicsJEIPlugin.O2O_CLICK_AREAS.size()]));
		registry.addRecipeClickArea(ScreenNuclearBoiler.class, 97, 31, 22, 15, NuclearBoilerRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenRadioactiveProcessor.class, 97, 31, 22, 15, RadioactiveProcessorRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenChemicalExtractor.class, 97, 31, 22, 15, ChemicalExtractorRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenGasCentrifuge.class, 44, 19, 50, 36, GasCentrifugeRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenReactorCore.class, 117, 43, 14, 13, FissionReactorRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenParticleInjector.class, 102, 33, 28, 14, ParticleAcceleratorAntiMatterRecipeCategory.UID, ParticleAcceleratorDarkMatterRecipeCategory.UID);
		registry.addRecipeClickArea(ScreenMSRFuelPreProcessor.class, 98, 40, 16, 16, MSRProcessorRecipeCategory.UID);
	}

	private static void nuclearScienceInfoTabs(IRecipeRegistration registration) {

		// BLOCKS
		for (ItemStack itemStack : NuclearSciencePsuedoRecipes.INFO_ITEMS) {
			registration.addIngredientInfo(itemStack, VanillaTypes.ITEM, new TranslatableComponent(INFO_BLOCK + itemStack.getItem().toString()));
		}

	}

}
