package gremsReliquary.patches.relics;

import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import gremsReliquary.relics.cursed.DiabolicDiabola;
import javassist.CtBehavior;

public class DiabolicDiabolaPatch {
    // Thank you SO much kio!
    
    private static float doubleDamage(AbstractCard card, float damage) {
        AbstractRelic nail = AbstractDungeon.player.getRelic(DiabolicDiabola.ID);
        if (nail != null && nail.counter == -42 && card.damageTypeForTurn == DamageInfo.DamageType.NORMAL) {
            damage *= 2;
            if (card.baseDamage != damage) {
                card.isDamageModified = true;
            }
        }
        return damage;
    }
    
    private static float doubleDamage(DamageInfo info, float damage) {
        
        AbstractRelic nail = AbstractDungeon.player.getRelic(DiabolicDiabola.ID);
        if (nail != null && nail.counter == -42 && info.type == DamageInfo.DamageType.NORMAL) {
            damage *= 2;
            if (info.base != damage) {
                info.isModified = true;
            }
        }
        
        return damage;
    }
    
    @SpirePatch(
            clz = AbstractCard.class,
            method = "applyPowers"
    )
    public static class ApplyPowersSingle {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(AbstractCard __instance, @ByRef float[] tmp) {
            tmp[0] = doubleDamage(__instance, tmp[0]);
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
    
    @SpirePatch(
            clz = AbstractCard.class,
            method = "applyPowers"
    )
    public static class ApplyPowersMulti {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp", "i"}
        )
        public static void Insert(AbstractCard __instance, float[] tmp, int i) {
            tmp[i] = doubleDamage(__instance, tmp[i]);
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
            }
        }
    }
    
    @SpirePatch(
            clz = AbstractCard.class,
            method = "calculateCardDamage"
    )
    public static class CalculateCardSingle {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, @ByRef float[] tmp) {
            tmp[0] = doubleDamage(__instance, tmp[0]);
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
    
    @SpirePatch(
            clz = AbstractCard.class,
            method = "calculateCardDamage"
    )
    public static class CalculateCardMulti {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp", "i"}
        )
        public static void Insert(AbstractCard __instance, AbstractMonster mo, float[] tmp, int i) {
            tmp[i] = doubleDamage(__instance, tmp[i]);
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return new int[]{LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher)[1]};
            }
        }
    }
    
    @SpirePatch(
            clz = DamageInfo.class,
            method = "applyPowers"
    )
    public static class DamageInfoApplyPowers {
        @SpireInsertPatch(
                locator = Locator.class,
                localvars = {"tmp"}
        )
        public static void Insert(DamageInfo __instance, AbstractCreature owner, AbstractCreature target, @ByRef float[] tmp) {
            tmp[0] = doubleDamage(__instance, tmp[0]);
        }
        
        private static class Locator extends SpireInsertLocator {
            @Override
            public int[] Locate(CtBehavior ctMethodToPatch) throws Exception {
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MathUtils.class, "floor");
                return LineFinder.findAllInOrder(ctMethodToPatch, finalMatcher);
            }
        }
    }
}